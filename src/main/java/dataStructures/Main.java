package dataStructures;

public class Main {
    public static void main(String[] args) {
        HashTable<String, String> hashTable = new HashTable<>();
        hashTable.put("Stas", "stas@test.com");
        hashTable.put("Taras", "taras@test.com");
        hashTable.put("Nikolas", "nikolas@test.com");
        hashTable.put("Alexiy", "Alexiy@test.com");
        hashTable.put("Ivan", "Ivan@test.com");
        hashTable.put("Maxim", "Maxim@test.com");
        hashTable.put("Ihor", "Ihor@test.com");
        hashTable.put("Kiril", "Kiril@test.com");
        hashTable.put("Vitaliy", "Vitaliy@test.com");
        hashTable.put("Dmitriy", "Dmitriy@test.com");
        hashTable.put("Sergiy", "Sergiy@test.com");
        hashTable.put("Vladimir", "Vladimir@test.com");
        hashTable.put("Petr", "Petr@test.com");
        hashTable.put("Miroslav", "Miroslav@test.com");
        hashTable.put("Nikolas", "NewNikolas@test.com");

        hashTable.printTable();
        hashTable.remove("Petr");
        hashTable.resize(50);
        hashTable.printTable();
    }
}
