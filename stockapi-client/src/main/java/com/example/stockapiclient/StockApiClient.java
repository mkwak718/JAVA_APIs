package com.example.stockapiclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class StockApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/stocks";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nStock API Client Menu:");
            System.out.println("1. Get all stocks");
            System.out.println("2. Get stock by symbol");
            System.out.println("3. Create a new stock");
            System.out.println("4. Update a stock");
            System.out.println("5. Delete a stock");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        getAllStocks();
                        break;
                    case 2:
                        System.out.print("Enter stock symbol: ");
                        String symbol = scanner.nextLine();
                        getStockBySymbol(symbol);
                        break;
                    case 3:
                        createStock(scanner);
                        break;
                    case 4:
                        updateStock(scanner);
                        break;
                    case 5:
                        System.out.print("Enter stock symbol to delete: ");
                        String deleteSymbol = scanner.nextLine();
                        deleteStock(deleteSymbol);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // GET /api/stocks
    private static void getAllStocks() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    // GET /api/stocks/{symbol}
    private static void getStockBySymbol(String symbol) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + symbol))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    // POST /api/stocks
    private static void createStock(Scanner scanner) throws Exception {
        System.out.print("Enter symbol: ");
        String symbol = scanner.nextLine();
        System.out.print("Enter company name: ");
        String companyName = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter volume: ");
        double volume = scanner.nextDouble();
        System.out.print("Enter market cap: ");
        double marketCap = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        String json = String.format(
                "{\"symbol\":\"%s\",\"companyName\":\"%s\",\"price\":%f,\"volume\":%f,\"marketCap\":%f}",
                symbol, companyName, price, volume, marketCap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    // PUT /api/stocks/{symbol}
    private static void updateStock(Scanner scanner) throws Exception {
        System.out.print("Enter symbol to update: ");
        String symbol = scanner.nextLine();
        System.out.print("Enter new company name: ");
        String companyName = scanner.nextLine();
        System.out.print("Enter new price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new volume: ");
        double volume = scanner.nextDouble();
        System.out.print("Enter new market cap: ");
        double marketCap = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        String json = String.format(
                "{\"symbol\":\"%s\",\"companyName\":\"%s\",\"price\":%f,\"volume\":%f,\"marketCap\":%f}",
                symbol, companyName, price, volume, marketCap);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + symbol))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }

    // DELETE /api/stocks/{symbol}
    private static void deleteStock(String symbol) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + symbol))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status: " + response.statusCode());
        System.out.println("Response: " + response.body());
    }
}