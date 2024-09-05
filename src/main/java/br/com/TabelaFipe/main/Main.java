package br.com.TabelaFipe.main;

import br.com.TabelaFipe.model.Data;
import br.com.TabelaFipe.model.Models;
import br.com.TabelaFipe.service.ApiConsumption;
import br.com.TabelaFipe.service.ConvertData;

import java.util.Comparator;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private ApiConsumption consumption = new ApiConsumption();
    private ConvertData convert = new ConvertData();

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
        var brands = convert.getList(json, Data.class);
        brands.stream()
                .sorted(Comparator.comparing(Data::code))
                .forEach(System.out::println);

        System.out.println("Enter the brand code for the query");
        var brandCode = scanner.nextLine();

        address = address + "/" + brandCode + "/modelos";
        json = consumption.getData(address);
        var modelList = convert.getData(json, Models.class);

        System.out.println("Models from this brand: ");
        modelList.models().stream()
                .sorted(Comparator.comparing(Data::code))
                .forEach(System.out::println);

    }
}
