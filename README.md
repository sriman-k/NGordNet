📚 NGordnet - CS 61B Project 2B
This project is a continuation of the NGordnet tool from CS 61B (Data Structures at UC Berkeley), focused on processing natural language data using WordNet and Google NGram datasets. Unlike Project 2A, Project 2B is open-ended in design, requiring thoughtful architecture, graph construction, and traversal to explore word relationships over time.

🚀 Getting Started
⚠️ Note: This setup is different from previous labs/projects. Please follow carefully.

✅ Clone Group Repository
Accept the GitHub repo invite (sent via email).

Go to Beacon > Groups tab and click "View Repository on GitHub".

Clone the repo:

bash
Copy
Edit
git clone <your-group-repo-link>
cd sp23-proj2b-g***  # Replace *** with your group number
Add skeleton as a remote and pull it:

bash
Copy
Edit
git remote add skeleton https://github.com/Berkeley-CS61B/proj2b-skeleton-sp23.git
git pull skeleton main --allow-unrelated-histories
📂 Project Structure
graphql
Copy
Edit
proj2b/
├── data/              # NGram and WordNet datasets
│   ├── ngrams/
│   └── wordnet/
├── ngordnet/          # Main source code
├── static/            # Frontend HTML interface (ngordnet.html)
├── library-sp23/      # Provided obfuscated classes (TimeSeries, NGramMap, etc.)
└── ...
🔧 Setup Notes
Pull the latest library with:

bash
Copy
Edit
cd library-sp23
git pull
Import the updated libraries into your project.

Download the WordNet and NGram datasets and place them in the data/ directory.

🧠 Key Features
🔍 Hyponyms Handler
Implements the Hyponyms feature in the frontend UI.

Reads and parses the WordNet dataset, constructs a directed graph of synset relationships.

Finds all hyponyms of a word or a list of words, including handling:

Multiple meanings (multi-synset words)

Word intersections across meanings

Alphabetical sorting and deduplication

📊 k-Most Frequent Hyponyms (Advanced Feature)
Supports a user-specified k to limit results to the k most frequent hyponyms in a date range.

Utilizes NGramMap to calculate historical word frequencies between startYear and endYear.

🧪 Testing
You can:

Use the browser interface (ngordnet.html) for visual tests.

Write JUnit tests for:

Graph creation

WordNet parsing

Hyponym lookup

k-frequency analysis

Example:

java
Copy
Edit
@Test
public void testHyponymsSimple() {
    WordNet wn = new WordNet("synsets11.txt", "hyponyms11.txt");
    assertEquals(Set.of("antihistamine", "actifed"), wn.hyponyms("antihistamine"));
}
🧰 Recommended Classes
Graph: Directed graph implementation

WordNet: Parses synsets/hyponyms and uses graph traversal to return hyponyms

HyponymsHandler: Web interface handler for user input

NGramMap: Provided library class for word frequency analysis

🎯 Goals & Learning Outcomes
Design and implement your own graph-based data structures

Process real-world linguistic datasets

Integrate backend logic with a web frontend

Handle file I/O, parsing, graph traversal, and data aggregation

👥 Partnerships
Project 2B is a partner project

Submit all code through your group GitHub repo

Make sure to fill out the Partnership Preferences Form by the posted deadline

📚 References
WordNet Visualizer

Staff Solution Webpage (CS 61B-only)

CS 61B Course Homepage

🧼 Tips for Success
Test incrementally (start with one-word cases, then multi-word, then k > 0)

Use Set and Map to handle deduplication and lookups efficiently

Avoid static/global variables—pass shared data via constructors

Keep your helper classes clean and single-responsibility

📜 License
This project is intended for educational purposes only and is part of UC Berkeley’s CS 61B curriculum.

