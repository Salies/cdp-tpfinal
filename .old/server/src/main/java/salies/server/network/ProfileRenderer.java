package salies.server.network;

import org.apache.pdfbox.io.IOUtils;
import salies.server.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ProfileRenderer implements Task<String>, Serializable {
    private String realName;
    private String username;
    private String bio;
    private String location;
    private Integer avatarOption;
    private Integer nFollowers;
    private Integer nFollowing;

    public ProfileRenderer(String realName, String username, String bio, String location, Integer avatarOption, Integer nFollowers, Integer nFollowing) {
        this.realName = realName;
        this.username = username;
        this.bio = bio;
        this.avatarOption = avatarOption;
        this.nFollowers = nFollowers;
        this.nFollowing = nFollowing;
        this.location = location;
    }

    private String generatePage() {
        // Carrega o template HTML dos resources
        InputStream template = getClass().getResourceAsStream("profile.html");
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(template));
        String htmlString = reader.lines().collect(Collectors.joining());

        // Substitui os placeholders pelos valores
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("%realname%", this.realName);
        placeholders.put("%username%", this.username);
        placeholders.put("%bio%", this.bio);
        placeholders.put("%followers%", this.nFollowers.toString());
        placeholders.put("%following%", this.nFollowing.toString());
        placeholders.put("%location%", this.location);
        // Caso especial: CSS
        InputStream css = getClass().getResourceAsStream("profile.min.css");
        BufferedReader cssReader = new BufferedReader(new java.io.InputStreamReader(css));
        String cssString = cssReader.lines().collect(Collectors.joining());
        placeholders.put("<style></style>", "<style>" + cssString + "</style>");
        // Substitui
        for (String key : placeholders.keySet()) {
            htmlString = htmlString.replace(key, placeholders.get(key));
        }

        // Carrega a imagem de perfil
        try {
            InputStream avatar = getClass().getResourceAsStream(this.avatarOption.toString() + ".png");
            assert avatar != null;
            byte[] bytes = IOUtils.toByteArray(avatar);
            String base64 = java.util.Base64.getEncoder().encodeToString(bytes);
            htmlString = htmlString.replace("%avatar%", "data:image/png;base64," + base64);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retorna o HTML
        return htmlString;
    }

    public String execute() {
        return generatePage();
    }
}
