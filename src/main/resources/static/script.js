const apiKey = '83aa758b074661e714f4e1843a462200';

document.addEventListener('DOMContentLoaded', () => {
    const movieDetailsContainer = document.querySelector('.movie-details-container');
    const searchForm = document.getElementById('search-form');
    const searchResults = document.getElementById('search-results');
    const recommendationsContainer = document.getElementById('recommendations-container');
    const similarMoviesContainer = document.getElementById('similar-movies-container');
    const sameActorsMoviesContainer = document.getElementById('same-actors-movies-container');
    const watchListContainer = document.getElementById('watch-list-container');
    const watchedListContainer = document.getElementById('watched-list-container');
    const clearResultsButton = document.getElementById('clear-results-button');
    let searchResultsData = JSON.parse(localStorage.getItem('searchResultsData')) || [];

    if (searchForm) {
        searchForm.addEventListener('submit', event => {
            event.preventDefault();
            const movieTitle = document.getElementById('movie-title').value;
            searchMovies(movieTitle);
        });
    }

    if (clearResultsButton) {
        clearResultsButton.addEventListener('click', () => {
            localStorage.removeItem('searchResultsData');
            searchResultsData = [];
            searchResults.innerHTML = '';
        });
    }

    async function searchMovies(title) {
        try {
            const response = await fetch(`https://api.themoviedb.org/3/search/movie?api_key=${apiKey}&query=${title}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const data = await response.json();
            searchResultsData = data.results;
            localStorage.setItem('searchResultsData', JSON.stringify(searchResultsData));
            displaySearchResults(searchResultsData);
        } catch (error) {
            console.error('Error fetching search results:', error);
        }
    }

    function displaySearchResults(movies) {
        if (!searchResults) return;
        searchResults.innerHTML = '';
        for (const movie of movies) {
            const movieElement = document.createElement('div');
            movieElement.className = 'movie-container';
            movieElement.innerHTML = `
                <img src="https://image.tmdb.org/t/p/w200${movie.poster_path}" alt="${movie.id}">
                <h3>${movie.title}</h3>
                <p><strong>Release Date:</strong> ${movie.release_date}</p>
                <p><strong>Rating:</strong> ${movie.vote_average}/10</p>
                <button onclick="showMovieDetails(${movie.id})">More Details</button>
                <button class="thumb-button thumb-up" onclick="thumbUp(${movie.id}, '${movie.title.replace(/'/g, "\\'")}', 'https://image.tmdb.org/t/p/w200${movie.poster_path}', '')">👍</button>
                <button class="thumb-button thumb-down" onclick="thumbDown(${movie.id}, 'search-results')">👎</button>
                <button onclick="redirectToRecommendations(${movie.id})">Get Recommendations</button>
            `;
            searchResults.appendChild(movieElement);
        }
    }

    window.thumbUp = function (movieId, title, posterPath, overview) {
        addToWatchList(movieId, title, posterPath, overview);
    };

    window.thumbDown = function (movieId, containerId) {
        const container = document.getElementById(containerId);
        if (container) {
            const movieElement = container.querySelector(`.movie-container img[alt="${movieId}"]`);
            if (movieElement) {
                movieElement.parentElement.remove();
                if (containerId === 'search-results') {
                    searchResultsData = searchResultsData.filter(movie => movie.id !== movieId);
                    localStorage.setItem('searchResultsData', JSON.stringify(searchResultsData));
                }
            }
        }
    };

    window.redirectToRecommendations = function (movieId) {
        localStorage.setItem('selectedMovieId', movieId);
        window.location.href = '/recommendations?id=' + movieId;
    };

    window.addToWatchList = async function (movieId, title, posterPath, overview) {
        try {
            const movieFromSearch = searchResultsData.find(movie => movie.id === movieId);
            const response = await fetch('/api/watchlist', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    movieId,
                    title,
                    posterPath,
                    overview,
                    releaseDate: movieFromSearch ? movieFromSearch.release_date : ''
                })
            });
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            alert('Movie added to watchlist.');
            displayWatchList();
        } catch (error) {
            console.error('Error adding to watchlist:', error);
        }
    };

    window.removeFromWatchList = async function (movieId) {
        try {
            const response = await fetch(`/api/watchlist/${movieId}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            alert('Movie removed from watchlist.');
            displayWatchList();
        } catch (error) {
            console.error('Error removing from watchlist:', error);
        }
    };

    window.removeFromWatchedList = async function (movieId) {
        try {
            const response = await fetch(`/api/watched/${movieId}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            alert('Movie removed from watched list.');
            displayWatchedList();
        } catch (error) {
            console.error('Error removing from watched list:', error);
        }
    };

    window.markAsWatched = async function (movieId) {
        try {
            const response = await fetch(`/api/watchlist/${movieId}/watched`, {
                method: 'POST'
            });
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            alert('Movie marked as watched.');
            displayWatchList();
            displayWatchedList();
        } catch (error) {
            console.error('Error marking as watched:', error);
        }
    };

    window.setRating = async function (movieId, rating) {
        try {
            const response = await fetch(`/api/movies/${movieId}/rate`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ rating })
            });
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            alert('Rating set.');
            displayWatchedList();
        } catch (error) {
            console.error('Error setting rating:', error);
        }
    };

    async function fetchWatchList() {
        try {
            const response = await fetch('/api/watchlist');
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const watchList = await response.json();
            return watchList;
        } catch (error) {
            console.error('Error fetching watchlist:', error);
            return [];
        }
    }

    async function fetchWatchedList() {
        try {
            const response = await fetch('/api/watched');
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const watchedList = await response.json();
            return watchedList;
        } catch (error) {
            console.error('Error fetching watched list:', error);
            return [];
        }
    }

    function displayWatchList() {
        fetchWatchList().then(watchList => {
            if (!watchListContainer) return;
            watchListContainer.innerHTML = '';
            watchList.forEach(movie => {
                const movieElement = document.createElement('div');
                movieElement.className = 'movie-container';
                movieElement.innerHTML = `
                    <img src="${movie.posterPath}" alt="${movie.title}">
                    <h3>${movie.title}</h3>
                    <button onclick="removeFromWatchList(${movie.id})">Remove from Watchlist</button>
                    <button onclick="markAsWatched(${movie.id})">Watched</button>
                    <button onclick="showMovieDetails(${movie.id})">More Details</button>
                `;
                watchListContainer.appendChild(movieElement);
            });
        });
    }

    function displayWatchedList() {
        fetchWatchedList().then(watchedList => {
            if (!watchedListContainer) return;
            watchedListContainer.innerHTML = '';
            watchedList.forEach(movie => {
                const movieElement = document.createElement('div');
                movieElement.className = 'movie-container';
                movieElement.innerHTML = `
                    <img src="${movie.posterPath}" alt="${movie.title}">
                    <h3>${movie.title}</h3>
                    <p><strong>My Rating:</strong> ${movie.rating || 0}/10</p>
                    <button onclick="removeFromWatchedList(${movie.id})">Remove from Watched</button>
                    <div class="rating">
                        ${[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map(star => `
                            <span onclick="setRating(${movie.id}, ${star})" class="${movie.rating >= star ? 'gold' : 'gray'}">★</span>
                        `).join('')}
                    </div>
                    <button onclick="showMovieDetails(${movie.id})">More Details</button>
                `;
                watchedListContainer.appendChild(movieElement);
            });
        });
    }

    // Remove review-related form from the watched list page
    function displayMovieReviews(movieId, reviews) {
        const reviewsContainer = document.getElementById('movie-reviews');
        if (reviewsContainer) {
            reviewsContainer.innerHTML = '<h3>Reviews:</h3>';
            reviews.forEach((review, index) => {
                const reviewElement = document.createElement('div');
                reviewElement.className = 'review';
                reviewElement.innerHTML = `<p><strong>Review ${index + 1}:</strong> ${review}</p>`;
                reviewsContainer.appendChild(reviewElement);
            });
        }
    }

    window.addReview = async function (event, movieId) {
        event.preventDefault();
        const reviewText = document.getElementById(`review-${movieId}`).value;
        if (!reviewText.trim()) {
            alert('Please enter a review before submitting.');
            return;
        }
        document.getElementById(`review-${movieId}`).value = ''; // Clear the textarea

        try {
            const response = await fetch('/api/reviews', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ movieId, reviewText })
            });
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const reviews = await fetchReviews(movieId);
            displayMovieReviews(movieId, reviews);
            alert('Review added!');
        } catch (error) {
            console.error('Error adding review:', error);
        }
    };

    window.showMovieDetails = async function (movieId) {
        try {
            const movie = await fetchMovieDetails(movieId);
            if (movie) {
                displayMovieDetails(movie);
                const reviews = await fetchReviews(movieId);
                displayMovieReviews(movieId, reviews);
            }
        } catch (error) {
            console.error('Error fetching movie details:', error);
        }
    };

    if (recommendationsContainer) {
        const movieId = localStorage.getItem('selectedMovieId');
        if (movieId) {
            fetch(`https://api.themoviedb.org/3/movie/${movieId}/recommendations?api_key=${apiKey}`)
                .then(response => response.json())
                .then(data => {
                    displayRecommendations(data.results);
                    fetchSimilarMovies(movieId);
                    fetchMoviesWithSameActors(movieId);
                })
                .catch(error => console.error('Error fetching recommendations:', error));
        }
    }

    function displayRecommendations(movies) {
        if (!recommendationsContainer) return;
        recommendationsContainer.innerHTML = '';
        movies.forEach(movie => {
            const movieElement = document.createElement('div');
            movieElement.className = 'movie-container';
            movieElement.innerHTML = `
                <img src="https://image.tmdb.org/t/p/w200${movie.poster_path}" alt="${movie.id}">
                <h3>${movie.title}</h3>
                <p><strong>Release Date:</strong> ${movie.release_date}</p>
                <p><strong>Rating:</strong> ${movie.vote_average}/10</p>
                <button onclick="showMovieDetails(${movie.id})">More Details</button>
                <button class="thumb-button thumb-up" onclick="thumbUp(${movie.id}, '${movie.title.replace(/'/g, "\\'")}', 'https://image.tmdb.org/t/p/w200${movie.poster_path}', '')">👍</button>
                <button class="thumb-button thumb-down" onclick="thumbDown(${movie.id}, 'recommendations-container')">👎</button>
                <button onclick="redirectToRecommendations(${movie.id})">Get Recommendations</button>
            `;
            recommendationsContainer.appendChild(movieElement);
        });
    }

    function fetchSimilarMovies(movieId) {
        fetch(`https://api.themoviedb.org/3/movie/${movieId}/similar?api_key=${apiKey}`)
            .then(response => response.json())
            .then(data => {
                displaySimilarMovies(data.results);
            })
            .catch(error => console.error('Error fetching similar movies:', error));
    }

    function displaySimilarMovies(movies) {
        if (!similarMoviesContainer) return;
        similarMoviesContainer.innerHTML = '';
        movies.forEach(movie => {
            const movieElement = document.createElement('div');
            movieElement.className = 'movie-container';
            movieElement.innerHTML = `
                <img src="https://image.tmdb.org/t/p/w200${movie.poster_path}" alt="${movie.id}">
                <h3>${movie.title}</h3>
                <p><strong>Release Date:</strong> ${movie.release_date}</p>
                <p><strong>Rating:</strong> ${movie.vote_average}/10</p>    
                <button onclick="showMovieDetails(${movie.id})">More Details</button>
                <button class="thumb-button thumb-up" onclick="thumbUp(${movie.id}, '${movie.title.replace(/'/g, "\\'")}', 'https://image.tmdb.org/t/p/w200${movie.poster_path}', '${movie.overview.replace(/'/g, "\\'")}')">👍</button>
                <button class="thumb-button thumb-down" onclick="thumbDown(${movie.id}, 'similar-movies-container')">👎</button>
                <button onclick="redirectToRecommendations(${movie.id})">Get Recommendations</button>
            `;
            similarMoviesContainer.appendChild(movieElement);
        });
    }

    function fetchMoviesWithSameActors(movieId) {
        fetch(`https://api.themoviedb.org/3/movie/${movieId}/credits?api_key=${apiKey}`)
            .then(response => response.json())
            .then(data => {
                const actors = data.cast.slice(0, 5);
                actors.forEach(actor => {
                    fetch(`https://api.themoviedb.org/3/discover/movie?api_key=${apiKey}&with_cast=${actor.id}`)
                        .then(response => response.json())
                        .then(data => {
                            displayMoviesWithSameActors(data.results);
                        })
                        .catch(error => console.error('Error fetching movies with same actors:', error));
                });
            })
            .catch(error => console.error('Error fetching credits:', error));
    }

    function displayMoviesWithSameActors(movies) {
        if (!sameActorsMoviesContainer) return;
        sameActorsMoviesContainer.innerHTML = '';
        movies.forEach(movie => {
            const movieElement = document.createElement('div');
            movieElement.className = 'movie-container';
            movieElement.innerHTML = `
                <img src="https://image.tmdb.org/t/p/w200${movie.poster_path}" alt="${movie.id}">
                <h3>${movie.title}</h3>
                <p><strong>Release Date:</strong> ${movie.release_date}</p>
                <p><strong>Rating:</strong> ${movie.vote_average}/10</p>    
                <button onclick="showMovieDetails(${movie.id})">More Details</button>
                <button class="thumb-button thumb-up" onclick="thumbUp(${movie.id}, '${movie.title.replace(/'/g, "\\'")}', 'https://image.tmdb.org/t/p/w200${movie.poster_path}', '${movie.overview.replace(/'/g, "\\'")}')">👍</button>
                <button class="thumb-button thumb-down" onclick="thumbDown(${movie.id}, 'same-actors-movies-container')">👎</button>
                <button onclick="redirectToRecommendations(${movie.id})">Get Recommendations</button>
            `;
            sameActorsMoviesContainer.appendChild(movieElement);
        });
    }

    async function fetchReviews(movieId) {
        try {
            const response = await fetch(`https://api.themoviedb.org/3/movie/${movieId}/reviews?api_key=${apiKey}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const data = await response.json();
            return data.results.map(review => review.content);
        } catch (error) {
            console.error('Error fetching reviews:', error);
            return [];
        }
    }

    async function fetchMovieDetails(movieId) {
        try {
            const response = await fetch(`https://api.themoviedb.org/3/movie/${movieId}?api_key=${apiKey}&append_to_response=credits`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const movie = await response.json();
            return movie;
        } catch (error) {
            console.error('Error fetching movie details:', error);
            return null;
        }
    }

    window.displayMovieDetails = function (movie) {
        document.getElementById('movie-title').textContent = movie.title;
        document.getElementById('movie-poster').src = `https://image.tmdb.org/t/p/w200${movie.poster_path}`;
        document.getElementById('movie-description').textContent = movie.overview;
        document.getElementById('movie-year').textContent = movie.release_date.split('-')[0];
        document.getElementById('movie-genre').textContent = movie.genres.map(genre => genre.name).join(', ');
        document.getElementById('movie-actors').textContent = movie.credits.cast.map(actor => actor.name).join(', ');
        document.getElementById('movie-director').textContent = movie.credits.crew.find(crew => crew.job === 'Director')?.name || 'N/A';
        document.getElementById('movie-producer').textContent = movie.credits.crew.find(crew => crew.job === 'Producer')?.name || 'N/A';
        document.getElementById('movie-budget').textContent = movie.budget ? `$${movie.budget.toLocaleString()}` : 'N/A';
    }

    if (window.location.pathname.includes('/details')) {
        const movieId = new URLSearchParams(window.location.search).get('id');
        if (movieId) {
            fetchMovieDetails(movieId).then(movie => {
                if (movie) {
                    displayMovieDetails(movie);
                    const reviews = JSON.parse(localStorage.getItem('selectedMovieReviews')) || [];
                    displayMovieReviews(movieId, reviews);

                    const reviewForm = document.createElement('form');
                    reviewForm.onsubmit = function (event) {
                        addReview(event, movieId);
                    };
                    reviewForm.innerHTML = `
                        <textarea id="review-${movieId}" placeholder="Add your review"></textarea>
                        <button type="submit">Submit Review</button>
                    `;
                    document.querySelector('.movie-details-container').appendChild(reviewForm);
                } else {
                    console.error('Movie not found or error fetching movie details.');
                }
            }).catch(error => console.error('Error fetching movie details:', error));
        }
    }
});
