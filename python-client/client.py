import requests

BASE_URL = "http://localhost:8080/api/stocks"

def get_all_stocks():
    try:
        response = requests.get(BASE_URL)
        print(f"Status: {response.status_code}")
        if response.status_code == 200:
            print("Response:", response.json())
        else:
            print("Error:", response.text)
    except requests.RequestException as e:
        print(f"Error: {e}")

def get_stock_by_symbol(symbol):
    try:
        response = requests.get(f"{BASE_URL}/{symbol}")
        print(f"Status: {response.status_code}")
        if response.status_code == 200:
            print("Response:", response.json())
        else:
            print("Error:", response.text)
    except requests.RequestException as e:
        print(f"Error: {e}")

def create_stock():
    try:
        symbol = input("Enter symbol: ")
        company_name = input("Enter company name: ")
        price = float(input("Enter price: "))
        volume = float(input("Enter volume: "))
        market_cap = float(input("Enter market cap: "))

        data = {
            "symbol": symbol,
            "companyName": company_name,
            "price": price,
            "volume": volume,
            "marketCap": market_cap
        }

        response = requests.post(BASE_URL, json=data)
        print(f"Status: {response.status_code}")
        if response.status_code == 200:
            print("Response:", response.json())
        else:
            print("Error:", response.text)
    except ValueError as e:
        print(f"Invalid input: {e}")
    except requests.RequestException as e:
        print(f"Error: {e}")

def update_stock():
    try:
        symbol = input("Enter symbol to update: ")
        company_name = input("Enter new company name: ")
        price = float(input("Enter new price: "))
        volume = float(input("Enter new volume: "))
        market_cap = float(input("Enter new market cap: "))

        data = {
            "symbol": symbol,
            "companyName": company_name,
            "price": price,
            "volume": volume,
            "marketCap": market_cap
        }

        response = requests.put(f"{BASE_URL}/{symbol}", json=data)
        print(f"Status: {response.status_code}")
        if response.status_code == 200:
            print("Response:", response.json())
        else:
            print("Error:", response.text)
    except ValueError as e:
        print(f"Invalid input: {e}")
    except requests.RequestException as e:
        print(f"Error: {e}")

def delete_stock():
    try:
        symbol = input("Enter stock symbol to delete: ")
        response = requests.delete(f"{BASE_URL}/{symbol}")
        print(f"Status: {response.status_code}")
        if response.status_code == 200:
            print("Stock deleted successfully")
        else:
            print("Error:", response.text)
    except requests.RequestException as e:
        print(f"Error: {e}")

def main():
    while True:
        print("\nStock API Client Menu:")
        print("1. Get all stocks")
        print("2. Get stock by symbol")
        print("3. Create a new stock")
        print("4. Update a stock")
        print("5. Delete a stock")
        print("6. Exit")
        choice = input("Choose an option (1-6): ")

        if choice == "1":
            get_all_stocks()
        elif choice == "2":
            symbol = input("Enter stock symbol: ")
            get_stock_by_symbol(symbol)
        elif choice == "3":
            create_stock()
        elif choice == "4":
            update_stock()
        elif choice == "5":
            delete_stock()
        elif choice == "6":
            print("Exiting...")
            break
        else:
            print("Invalid option. Try again.")

if __name__ == "__main__":
    main()