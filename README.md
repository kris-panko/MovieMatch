# MovieMatch

## Overview

MovieMatch is a web application that allows users to search for movies, get recommendations, and manage a watchlist. The project is built using HTML, CSS, and JavaScript, leveraging the TMDB API for movie data.

## Features

- **Search for Movies**: Users can search for movies by title. Users can see more details and thumbs up or thumbs down a movie to add to watchlist or remove from search. 
- **Movie Recommendations**: Provides movie recommendations based on the selected movie, with sub-categories for similar movies and movies with similar actors. 
- **Watchlist Management**: Users can add movies to their watchlist and mark them as watched. Users can then rate watched movies. Users can also remove movies from Watched and Watchlist. 
- **Details**: Displays movie details like year, rating, actors, as well as additional movie details like director, producer, and budget.
- **Review System**: Users can read and add reviews to movies.

## Technical Specifications

### HTML

- **Pages**:
  - `index.html`: Home page with a search form.
  - `recommendations.html`: Page to display movie recommendations.
  - `watchlist.html`: Page to manage the user's watchlist.
  - `details.html`: Page to display detailed information about a selected movie.
- **Tags**: Utilizes various HTML tags including `div`, `h1`, `h2`, `p`, `img`, `a`, `ul`, `li`, `form`, `input`, `button`, `textarea`, `table`, `th`, `td`.
- **Tables**: Added in `details.html` to display additional movie details.
- **Forms**: Implemented in `index.html` (search form) and `details.html` (review form).
- **Dropdown Menu**: Navigation menus on all pages.
- **Web Fonts**: Google Fonts (Roboto) included in all HTML files.
- **Content Types**: Includes text and images (optional: videos and GIFs).
- **Regex Validation**: Used in the search form in `index.html`.

### CSS

- **Styles**:
  - **Inline**: Styles added directly to HTML elements.
  - **Internal**: `<style>` tags in `details.html`.
  - **External**: `style.css` linked in all HTML files.
- **Selectors**: Utilizes various CSS selectors including class, ID, element, pseudo-classes, and attribute selectors.
- **Colors**: Consistent and complementary color scheme across the project.

### JavaScript

- **External Scripts**: `script.js` linked in all HTML files.
- **Functionality**:
  - **Variables**: Used throughout `script.js`.
  - **If Statements**: Used in functions within `script.js`.
  - **Loops**: Used for iterating over arrays (e.g., display search results, watchlist, and watched list).
  - **Collections**: Arrays used for managing data (`watchList`, `watchedList`, `searchResultsData`).
  - **Functions/Callbacks**: Multiple functions implemented (e.g., `searchMovies`, `displaySearchResults`, `addToWatchList`).
  - **Events**: Event listeners for form submissions and button clicks.
- **API Integration**: Uses TMDB API for fetching movie data.
- **Local Storage**: Stores watchlist and search results in the browser's local storage.

## Installation

1. Clone the repository: `git clone https://github.com/yokris-panko/moviematch.git`
2. Open the project directory: `cd moviematch`
3. Open `index.html` in your preferred browser.

## Usage

1. **Search for Movies**: Enter a movie title in the search form on the home page and click "Search".
2. **View Details**: Click "More Details" on any movie to view detailed information.
3. **Add to Watchlist**: Click the thumbs-up button to add a movie to your watchlist.
4. **Manage Watchlist**: Navigate to the watchlist page to view and manage your watchlist. Mark movies as watched or remove them from the watchlist.
5. **Get Recommendations**: Click "Get Recommendations" on any movie to view similar movies and movies with the same actors.

## Project Structure

moviematch/
├── index.html
├── recommendations.html
├── watchlist.html
├── details.html
├── style.css
└── script.js

markdown
Copy code

## Deployment

The project is hosted on GitHub Pages: [MovieMatch](https://kris-panko.github.io/moviematch)

## Contributing

1. Fork the repository.
2. Create your feature branch: `git checkout -b feature/my-new-feature`.
3. Commit your changes: `git commit -m 'Add some feature'`.
4. Push to the branch: `git push origin feature/my-new-feature`.
5. Submit a pull request.

## License

This project is licensed under the MIT License.

## Acknowledgements

- TMDB API for providing movie data.
- Google Fonts for the Roboto font.
- Inspiration and guidance from the Java Full Stack Development course.
