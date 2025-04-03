import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String cep = "";
        List<String> resultados = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        HttpClient client = HttpClient.newHttpClient();


        while (!cep.equalsIgnoreCase("sair")) {
            System.out.println("Digite um cep com 8 dígitos: ");
            cep = sc.nextLine();

            if(cep.equalsIgnoreCase("sair")){
                break;
            }
            String endereco = "https://viacep.com.br/ws/" + cep + "/json/";

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();

                System.out.println("Resposta da API: " + json);
                resultados.add(json);
            }catch (IOException | InterruptedException e){
                System.out.println("Erro ao buscar o CEP: " + e.getMessage());
            }
        }

        try(FileWriter writer = new FileWriter("enderecos.json")){
            writer.write(gson.toJson(resultados));
            System.out.println("Dados salvos em enderecos.json");
        }catch (IOException e){
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }

    }
}