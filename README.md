# LegacyTab

LegacyTab is a powerful 1.8 Tablist API designed to manipulate the tablist in Minecraft clients by sending fake player packets, Legacy-Tab allows developers to dynamically display custom information, such as statistics, player roles, or game state data, directly in the player's tab list.

---

## Features
- **Broad Compatibility**: Built for Spigot 1.8.8, Works seamlessly with Minecraft clients from 1.7 through 1.21.4.
- **Custom Display**: Populate the tab list with any information by utilizing fake player packets.
- **Lightweight**: Uses its own dedicated thread for minimal performance impact on the server.
- **Developer-Friendly**: Easy-to-use API for integrating with existing plugins or projects.
---

[![ezgif-3-e6479d8a4e.gif](https://i.postimg.cc/L5x3shmV/ezgif-3-e6479d8a4e.gif)]([https://i.postimg.cc/L5x3shmV/ezgif-3-e6479d8a4e.gif](https://i.postimg.cc/L5x3shmV/ezgif-3-e6479d8a4e.gif))

## Installation
1. Download the latest version of the Legacy-Tab jar from the [Releases](#).
2. Add the jar to your project's dependencies.
3. Import the API into your codebase and start customizing your tab list.

---

## Usage
To start using Legacy-Tab, you simply need to initialize the API and send your desired tab adapter. Here's a basic example:

Initialize with your plugin's instance:
```java
// Initialize LegacyTab in your onEnable
  LegacyTab legacyTab = new LegacyTab(plugin);
```

Create a Tab Adapter (Also supports anonymous classes)
```java
public class ExampleAdapter extends TabAdapter {

    @Override
    public HashMap<Integer, TabItem> getItems(WrappedTabPlayer tabPlayer) {
        HashMap<Integer, TabItem> items = new HashMap<>();
        
        items.put(0, new TabItem() //Set the tab item of the first slot
                .setPing(0) //Set 0 ping to hide it on most pvp clients
                .setSkin(Skin.DEAD) //Use the built-in Skins or Cache skins using 'new Skin(UUID)'
                .setText("&cHello World!")); //Sets the text displayed on the fake player

        return items;
    }

    @Override
    public void tick() { } //Called every tick (50ms). Can be useful for time handling
}
```

Set a player's TabAdapter
```java
  Player player = event.getPlayer();
  TabAdapter tabAdapter = new ExampleAdapter();

  legacyTab.setAdapter(player, tabAdapter);
```

---
