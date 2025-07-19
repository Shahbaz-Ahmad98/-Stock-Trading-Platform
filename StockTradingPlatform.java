import java.util.ArrayList;
import java.util.HashMap;

// Stock class
class Stock {
    String symbol;
    double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }
}

// Transaction class
class Transaction {
    String stockSymbol;
    int quantity;
    double priceAtTransaction;
    String type;  // "BUY" or "SELL"

    public Transaction(String stockSymbol, int quantity, double priceAtTransaction, String type) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.priceAtTransaction = priceAtTransaction;
        this.type = type;
    }
}

// Portfolio class
class Portfolio {
    HashMap<String, Integer> holdings = new HashMap<>();
    ArrayList<Transaction> transactions = new ArrayList<>();
    double cashBalance = 10000.0; // Starting cash

    public void buyStock(Stock stock, int quantity) {
        double totalCost = stock.price * quantity;
        if (totalCost > cashBalance) {
            System.out.println("Insufficient funds to buy " + quantity + " shares of " + stock.symbol);
        } else {
            holdings.put(stock.symbol, holdings.getOrDefault(stock.symbol, 0) + quantity);
            cashBalance -= totalCost;
            transactions.add(new Transaction(stock.symbol, quantity, stock.price, "BUY"));
            System.out.println("Bought " + quantity + " shares of " + stock.symbol);
        }
    }

    public void sellStock(Stock stock, int quantity) {
        int owned = holdings.getOrDefault(stock.symbol, 0);
        if (owned < quantity) {
            System.out.println("Not enough shares to sell of " + stock.symbol);
        } else {
            holdings.put(stock.symbol, owned - quantity);
            cashBalance += stock.price * quantity;
            transactions.add(new Transaction(stock.symbol, quantity, stock.price, "SELL"));
            System.out.println("Sold " + quantity + " shares of " + stock.symbol);
        }
    }

    public void showPortfolio(HashMap<String, Stock> marketStocks) {
        System.out.println("\n--- Portfolio Summary ---");
        for (String symbol : holdings.keySet()) {
            int qty = holdings.get(symbol);
            double marketPrice = marketStocks.get(symbol).price;
            System.out.println(symbol + ": " + qty + " shares, Market Price: $" + marketPrice);
        }
        System.out.println("Cash Balance: $" + cashBalance);
    }

    public void showTransactions() {
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : transactions) {
            System.out.println(t.type + " " + t.quantity + " shares of " + t.stockSymbol + " @ $" + t.priceAtTransaction);
        }
    }
}

// Main Trading Platform
public class StockTradingPlatform {
    public static void main(String[] args) {
        // Sample Market Data
        HashMap<String, Stock> marketStocks = new HashMap<>();
        marketStocks.put("AAPL", new Stock("AAPL", 180));
        marketStocks.put("GOOG", new Stock("GOOG", 2500));
        marketStocks.put("TSLA", new Stock("TSLA", 720));

        Portfolio userPortfolio = new Portfolio();

        // Display market data
        System.out.println("--- Market Data ---");
        for (Stock stock : marketStocks.values()) {
            System.out.println(stock.symbol + ": $" + stock.price);
        }

        // Simulate Transactions
        userPortfolio.buyStock(marketStocks.get("AAPL"), 20);
        userPortfolio.buyStock(marketStocks.get("TSLA"), 5);
        userPortfolio.sellStock(marketStocks.get("AAPL"), 5);

        // Show Portfolio and Transaction History
        userPortfolio.showPortfolio(marketStocks);
        userPortfolio.showTransactions();
    }
}
