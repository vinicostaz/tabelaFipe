package br.com.TabelaFipe.main;

import br.com.TabelaFipe.service.ApiConsumption;

import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private ApiConsumption consumption = new ApiConsumption();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void showMenu() {
        var menu = """
                *** OPTIONS ***
                Carro (Car)
                Moto (Motorcycle)
                Caminhão (Truck)
                
                Type one of the options to consult:
                """;

        System.out.println(menu);
        var option = scanner.nextLine();
        String address;

        if(option.toLowerCase().contains("carr")) {
            address = URL_BASE + "carros/marcas";
        } else if (option.toLowerCase().contains("mot")) {
            address = URL_BASE + "motos/marcas";
        } else {
            address = URL_BASE + "caminhoes/marcas";
        }

        var json = consumption.getData(address);
        System.out.println(json);

    }
}
