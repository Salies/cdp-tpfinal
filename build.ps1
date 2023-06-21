# Compilando o compute, a ser usado pelo server e pelo executor
javac compute/Compute.java compute/Task.java
jar cvf classes/compute.jar compute/*.class

# Compilando o executor e os módulos do servidor
javac -cp "./classes/compute.jar" runner/Runner.java server/hashing/*.java server/network/ProfileRenderer.java server/stats/DataStats.java

# Compilando o servidor
javac -cp "./classes/compute.jar;." server/Server.java server/ClientHandler.java
