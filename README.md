<h1 align="Left"> Serviço de Sincronização de dados bancarios SICREDI </h1>
<h2> ## :hammer: Funcionalidades do projeto Sincronização de dados:</h2>
- `Sincronização via arquivo`: E essa funcionalidade faz a sincronização de dados via arquivo csv no formato padrão especificado.<br>
- `Geração do JAR`: Gerar o JAR apartir da raiz do projeto, executar o comando `mvn package -DskipTests`.<br>
- `Execução do JAR`: Apos gerar o JAR, executar como seguinte comando `java -jar sincronizacao-0.0.1-SNAPSHOT.jar nomedoarquivo.csv` o mesmo sera processado e salvar um novo arquivo como nome `nomedoarquivo_datadehoje_.csv`<br><br>

  <h2>## 📁 Formato do Arquivo csv</h2
  <h3>agencia;conta;saldo;status<br>
  0101;12225-6;100,00;A<br>
  0101;12226-8;3200,50;A<br>
  3202;40011-1;-35,12;I<br>
  3202;54001-2;0,00;P<br>
  3202;00321-2;34500,00;B</h3><br>
  
## ✔️ Tecnologias utilizadas
- ``Java 11``
- ``ECLIPSE``
