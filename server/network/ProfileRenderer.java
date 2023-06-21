package server.network;

import compute.Task;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.io.InputStreamReader;

/*
 * A ideia deste serviço é implementar um template engine básico que opera
 * sobre um HTML, dadas informações passadas como parâmetro à classe.
 * Para um entendimento básico do que é um template engine, leia:
 * https://en.wikipedia.org/wiki/Template_processor.
 * No caso, reduzi o problema para operar sobre uma página só, claro, para evitar complexidade
 * devido a possíveis problemas e funcionalidades desnecessárias (insersção de páginas, dependências cross-page, etc). .
 */
public class ProfileRenderer implements Task<String>, Serializable {
    private static final long serialVersionUID = 666L;

    private String realName;
    private String username;
    private String bio;
    private String location;
    private Integer avatarOption;
    private Integer nFollowers;
    private Integer nFollowing;
    // Recursos do servidor
    private String html;
    private String css;
    private byte[] avatar;


    public ProfileRenderer(String realName, String username, String bio, String location, Integer avatarOption, Integer nFollowers, Integer nFollowing) {
        this.realName = realName;
        this.username = username;
        this.bio = bio;
        this.avatarOption = avatarOption;
        this.nFollowers = nFollowers;
        this.nFollowing = nFollowing;
        this.location = location;

        // Carregando os recursos do servidor
        // HTML
        InputStream template = this.getClass().getResourceAsStream("resources/profile.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(template));
        this.html = reader.lines().collect(Collectors.joining());
        // CSS
        InputStream css = getClass().getResourceAsStream("resources/profile.min.css");
        BufferedReader cssReader = new BufferedReader(new InputStreamReader(css));
        this.css = cssReader.lines().collect(Collectors.joining());
        // Avatar
        // As imagens são definidas para evitar possíveios problemas de BufferOverflow com imagens muito grandes
        // sendo passadas pelo socket.
        InputStream avatar = getClass().getResourceAsStream("resources/" + this.avatarOption.toString() + ".png");
        assert avatar != null;
        this.avatar = new byte[0];
        try {
            this.avatar = new byte[avatar.available()];
            avatar.read(this.avatar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Gerando a página a partir dos dados passados e recursos do servidor enviados.
    // É algo que faz sentido ser feito remotamente, pois a operação de conversão de imagem para base64
    // é uma operação custosa. Todavia, trata-se apenas de um exemplo de um server-side rendering composto.
    private String generatePage() {
        String htmlString = this.html;
        // Criando mapa para troca dos placeholders pelos valores
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("%realname%", this.realName);
        placeholders.put("%username%", this.username);
        placeholders.put("%bio%", this.bio);
        placeholders.put("%followers%", this.nFollowers.toString());
        placeholders.put("%following%", this.nFollowing.toString());
        placeholders.put("%location%", this.location);
        String cssString = this.css;
        placeholders.put("<style></style>", "<style>" + cssString + "</style>");

        // Substituindo
        for (String key : placeholders.keySet()) {
            htmlString = htmlString.replace(key, placeholders.get(key));
        }

        String base64 = new String(Base64.getEncoder().encodeToString(this.avatar));

        htmlString = htmlString.replace("%avatar%", "data:image/png;base64," + base64);

        // Retorna o HTML
        return htmlString;
    }

    public String execute() {
        return generatePage();
    }
}
