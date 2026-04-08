package hashtagManagement;
import java.io.*;
import java.util.*;
//goal to detect hashtags and store in a map

public class HashManage {

    // Map<String,vector<String>> mp = store.hashtagPosts;

    // strings->Number of hashs globally i will store
    // rather not the count but the string of all POSTIDS
    // so i have to go through all the Posts in txt file

    // public void extractHash(String content) {
    // // List<String> tags = new ArrayList<>();
    // String[] words = content.split("\\s+");

    // for (String word : words) {
    // if (word.startsWith("#") && word.length() > 1) {
    // String z = word.substring(1);
    // // int c = store.hashtagPosts.get(z).size();
    // if (!store.hashtagPosts.containsKey(z)) {
    // store.hashtagPosts.put(z, new ArrayList<>());
    // }

    // store.hashtagPosts.get(z).add(content);

    // }
    // }
    // // return String.join(",", tags);
    // }

    public void extractHash(String content) {

        String[] words = content.split("\\s+");
        Set<String> uniqueTags = new HashSet<>();

        for (String word : words) {
            if (word.startsWith("#") && word.length() > 1) {
                String cleanTag = word.substring(1).replaceAll("[^a-zA-Z0-9._]", "").toLowerCase();

                if (!cleanTag.isEmpty()) {
                    uniqueTags.add(cleanTag);
                }
            }
        }

        for (String z : uniqueTags) {
            if (!store.hashtagPosts.containsKey(z)) {
                store.hashtagPosts.put(z, new ArrayList<>());
            }

            store.hashtagPosts.get(z).add(content);
        }
    }

    public void extractCOUNTS(String content) {

        String[] words = content.split("\\s+");
        Set<String> uniqueTags = new HashSet<>();

        for (String word : words) {
            if (word.startsWith("#") && word.length() > 1) {
                String cleanTag = word.substring(1).replaceAll("[^a-zA-Z0-9._]", "").toLowerCase();

                if (!cleanTag.isEmpty()) {
                    uniqueTags.add(cleanTag);
                }
            }
        }

        for (String z : uniqueTags) {
            int count = store.hashsCount.getOrDefault(z, 0);
            store.hashsCount.put(z, count + 1);
        }
    }

    public void loadPast() {
        store.hashtagPosts.clear();
        store.hashsCount.clear();

        try {
            File file = new File("posts.txt");
            if (!file.exists())
                return;

            try (Scanner fs = new Scanner(file)) {
                while (fs.hasNextLine()) {
                    String line = fs.nextLine();
                    String[] parts = line.split("\\|");

                    if (parts.length >= 3) {
                        String content = parts[2];
                        extractHash(content);
                        extractCOUNTS(content);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void display(String keyword) {

        loadPast();
        keyword = keyword.replaceAll("[^a-zA-Z0-9._]", "").toLowerCase();

        if (!store.hashtagPosts.containsKey(keyword)) {
            System.out.println("No posts from this tag!!");
            return;
        }

        for (String g : store.hashtagPosts.get(keyword)) {
            System.out.println(g);
            System.out.println("\n");
        }
    }

    public void displayTrending(int topN) {

        List<Map.Entry<String, Integer>> list = new ArrayList<>(store.hashsCount.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("Top " + topN + " Trending Hashtags:");

        for (int i = 0; i < Math.min(topN, list.size()); i++) {
            Map.Entry<String, Integer> entry = list.get(i);
            System.out.println("#" + entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Now to do is basically is just view and display all the corresponding if user
// calls