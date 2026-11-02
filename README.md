# Edinlode-Game-Project-Java-2026
A 2D platformer style rpg game made based on a map I made a few years back.

As a fantasy story fan, I had always wanted to create something based off of the fantasy map, whether that was a book or a game. But as time passed and I grew up with dreams, I realized writing was not in my line of strengths. But programming was. So I easily decided to make a game. As a gamer at heart, I play a lot of games, and
my topmost references were Maplestory and Diablo IV, and I wanted something that would merge the two together, keeping my fantasy world's original idea and background as well as the lore while taking the system and designs of popular games to set a starting step.

So far, I have learnt more about the Java language than I ever have, and I am currently enjoying every single time I open the app to test how it brings my dream idea to life, and when I see how much code I have written and come through. I hope I keep enjoying these bits of my code.

How it works:

Move Left (left arrow key, A)
Move Right (right arrow key, D)
Jump (space)
Drink Potion (X or click the small circle on the left)
Dash (shift or click the small circle on the right)
Inventory (I to turn on and off)
Map (should open to Celenode; right click to go back (zoom out) of the map and left click labels to enter that map)
Currently no attack systems made, no save systems made, still in production.

### 🔧 Installation Instructions (Multi-part Download)
Due to GitHub's file size restrictions, this release is split into separate parts. 

1. **Download all `.part` files** (`part0` through `part5`) into the exact same folder on your computer.
2. Open **PowerShell** in that folder (Shift + Right-click in the folder -> *Open PowerShell window here* or *Open in Terminal*).
3. Run the matching command below for your operating system to combine them:

#### For Windows 11 (Fastest):
```powershell
cmd /c "copy /b Edinlode 1.0.0-alpha.3.zip.part* Edinlode_Full.zip"
```

#### For Windows 10:
```powershell
Get-Content "Edinlode 1.0.0-alpha.3.zip.part*" -AsByteStream | Set-Content "Edinlode_Full.zip" -AsByteStream
```

4. Once finished, a new file named **`Edinlode_Full.zip`** will appear. Right-click it and extract it normally to play!
