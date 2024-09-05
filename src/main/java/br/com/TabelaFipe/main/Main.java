package br.com.TabelaFipe.main;

import br.com.TabelaFipe.model.Data;
import br.com.TabelaFipe.model.Models;
import br.com.TabelaFipe.model.Vehicle;
import br.com.TabelaFipe.service.ApiConsumption;
import br.com.TabelaFipe.service.ConvertData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
                
                Enter one of the options to consult:
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

        System.out.println("\nEnter the brand code for the query");
        var brandCode = scanner.nextLine();

        address = address + "/" + brandCode + "/modelos";
        json = consumption.getData(address);
        var modelList = convert.getData(json, Models.class);

        System.out.println("\nModels from this brand: ");
        modelList.models().stream()
                .sorted(Comparator.comparing(Data::code))
                .forEach(System.out::println);


        System.out.println("\nEnter part of the car name to be searched");
        var vehicleName = scanner.nextLine();

        List<Data> filteredModels = modelList.models().stream()
                .filter(m -> m.name().toLowerCase().contains(vehicleName.toLowerCase()))
                .toList();

        System.out.println("\nFiltered models: ");
        filteredModels.forEach(System.out::println);

        System.out.println("\nEnter the model code to search for the evaluation values: ");
        var modelCode = scanner.nextLine();

        address = address + "/" + modelCode + "/anos";
        json = consumption.getData(address);
        List<Data> years = convert.getList(json, Data.class);
        List<Vehicle> vehicles = new ArrayList<>();

        for(int i = 0; i < years.size(); i++) {
            var addressYears = address + "/" + years.get(i).code();
            json = consumption.getData(addressYears);
            Vehicle vehicle = convert.getData(json, Vehicle.class);
            vehicles.add(vehicle);
        }

        System.out.println("\nAll vehicles filtered with evaluations by year: ");
        vehicles.forEach(System.out::println);

    }
}
