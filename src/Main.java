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

        System.out.println("Digite um número de CEP para consulta: ");
        var cep = sc.nextLine();

        ConsultaCEP consulta = new ConsultaCEP();

        try{
            Endereco novoEndereco = consulta.buscaEndereco(cep);
            System.out.println(novoEndereco);
            GeradorDeArquivos gerador = new GeradorDeArquivos();
            gerador.geraJson(novoEndereco);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            System.out.println("Finalizando aplicação");
        }

    }
}