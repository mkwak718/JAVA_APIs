$(document).ready(function() {
    const BASE_URL = "http://localhost:8080/api/stocks";

    // Load all stocks on page load
    loadStocks();

    // Handle form submission (Add/Update)
    $("#stockForm").submit(function(event) {
        event.preventDefault();
        const symbolHidden = $("#symbolHidden").val();
        const stock = {
            symbol: $("#symbol").val(),
            companyName: $("#companyName").val(),
            price: parseFloat($("#price").val()),
            volume: parseFloat($("#volume").val()),
            marketCap: parseFloat($("#marketCap").val())
        };

        if (symbolHidden) {
            // Update stock
            $.ajax({
                url: `${BASE_URL}/${symbolHidden}`,
                type: "PUT",
                contentType: "application/json",
                data: JSON.stringify(stock),
                success: function() {
                    alert("Stock updated successfully!");
                    loadStocks();
                    clearForm();
                },
                error: function(xhr) {
                    alert("Error updating stock: " + xhr.responseText);
                }
            });
        } else {
            // Create stock
            $.ajax({
                url: BASE_URL,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(stock),
                success: function() {
                    alert("Stock added successfully!");
                    loadStocks();
                    clearForm();
                },
                error: function(xhr) {
                    alert("Error adding stock: " + xhr.responseText);
                }
            });
        }
    });

    // Handle search by symbol
    $("#searchBtn").click(function() {
        const symbol = $("#searchSymbol").val().trim();
        console.log(symbol);
        if (symbol) {
            $.ajax({
                url: `${BASE_URL}/${symbol}`,
                type: "GET",
                success: function(data) {
                    displayStocks([data]);
                },
                error: function(xhr) {
                    alert("Error: " + xhr.responseText);
                }
            });
        } else {
            alert("Please enter a symbol to search.");
        }
    });

    // Reset to show all stocks
    $("#resetBtn").click(function() {
        $("#searchSymbol").val("");
        loadStocks();
    });

    // Clear form
    $("#clearBtn").click(function() {
        clearForm();
    });

    // Load all stocks
    function loadStocks() {
        $.ajax({
            url: BASE_URL,
            type: "GET",
            success: function(data) {
                displayStocks(data);
            },
            error: function(xhr) {
                alert("Error loading stocks: " + xhr.responseText);
            }
        });
    }

    // Display stocks in table
    function displayStocks(stocks) {
        $("#stockTableBody").empty();
        stocks.forEach(stock => {
            $("#stockTableBody").append(`
                <tr>
                    <td>${stock.symbol}</td>
                    <td>${stock.companyName}</td>
                    <td>${stock.price.toFixed(2)}</td>
                    <td>${stock.volume.toLocaleString()}</td>
                    <td>${stock.marketCap.toLocaleString()}</td>
                    <td>
                        <button class="edit-btn" onclick="editStock('${stock.symbol}')">Edit</button>
                        <button class="delete-btn" onclick="deleteStock('${stock.symbol}')">Delete</button>
                    </td>
                </tr>
            `);
        });
    }

    // Edit stock (populate form)
    window.editStock = function(symbol) {
        $.ajax({
            url: `${BASE_URL}/${symbol}`,
            type: "GET",
            success: function(data) {
                $("#symbolHidden").val(data.symbol);
                $("#symbol").val(data.symbol);
                $("#companyName").val(data.companyName);
                $("#price").val(data.price);
                $("#volume").val(data.volume);
                $("#marketCap").val(data.marketCap);
                $("#submitBtn").text("Update Stock");
                $("#symbol").prop("disabled", true); // Disable symbol input during update
            },
            error: function(xhr) {
                alert("Error: " + xhr.responseText);
            }
        });
    };

    // Delete stock
    window.deleteStock = function(symbol) {
        if (confirm(`Are you sure you want to delete ${symbol}?`)) {
            $.ajax({
                url: `${BASE_URL}/${symbol}`,
                type: "DELETE",
                success: function() {
                    alert("Stock deleted successfully!");
                    loadStocks();
                },
                error: function(xhr) {
                    alert("Error deleting stock: " + xhr.responseText);
                }
            });
        }
    };

    // Clear form
    function clearForm() {
        $("#stockForm")[0].reset();
        $("#symbolHidden").val("");
        $("#submitBtn").text("Add Stock");
        $("#symbol").prop("disabled", false);
    }
});