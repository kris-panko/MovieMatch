const apiKey = '83aa758b074661e714f4e1843a462200';

document.addEventListener('DOMContentLoaded', () => {

    const searchForm = document.getElementById('search-form');
    const searchResults = document.getElementById('search-results');
    const recommendationsContainer = document.getElementById('recommendations-container');
    const similarMoviesContainer = document.getElementById('similar-movies-container');
    const sameActorsMoviesContainer = document.getElementById('same-actors-movies-container');
    const watchListContainer = document.getElementById('watch-list-container');
    const watchedListContainer = document.getElementById('watched-list-container');
    const clearResultsButton = document.getElementById('clear-results-button');
    let watchList = JSON.parse(localStorage.getItem('watchList')) || [];
    let watchedList = JSON.parse(localStorage.getItem('watchedList')) || [];
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
        window.location.href = '/recommendations';
    };

    window.addToWatchList = function (movieId, title, posterPath, overview) {
        const movieFromSearch = searchResultsData.find(movie => movie.id === movieId);
        if (watchList.some(movie => movie.id === movieId) || watchedList.some(movie => movie.id === movieId)) {
            alert('This movie is already in your watchlist or watched list.');
            return;
        }
        const movie = {
            id: movieId,
            title,
            posterPath,
            overview,
            rating: 0,
            release_date: movieFromSearch ? movieFromSearch.release_date : ''
        };
        watchList.push(movie);
        localStorage.setItem('watchList', JSON.stringify(watchList));
        alert('Movie added to watchlist.');
        displayWatchList();
    };

    window.removeFromWatchList = function (movieId) {
        watchList = watchList.filter(movie => movie.id !== movieId);
        localStorage.setItem('watchList', JSON.stringify(watchList));
        displayWatchList();
    };

    window.removeFromWatchedList = function (movieId) {
        watchedList = watchedList.filter(movie => movie.id !== movieId);
        localStorage.setItem('watchedList', JSON.stringify(watchedList));
        displayWatchedList();
    };

    window.markAsWatched = function (movieId) {
        const movie = watchList.find(movie => movie.id === movieId);
        if (movie) {
            watchList = watchList.filter(movie => movie.id !== movieId);
            watchedList.push(movie);
            localStorage.setItem('watchList', JSON.stringify(watchList));
            localStorage.setItem('watchedList', JSON.stringify(watchedList));
            displayWatchList();
            displayWatchedList();
        }
    };

    window.setRating = function (movieId, rating) {
        const movie = watchedList.find(movie => movie.id === movieId);
        if (movie) {
            movie.rating = rating;
            localStorage.setItem('watchedList', JSON.stringify(watchedList));
            displayWatchedList();
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
            `;
            sameActorsMoviesContainer.appendChild(movieElement);
        });
    }

    window.showMovieDetails = async function (movieId) {
        localStorage.setItem('selectedMovieId', movieId);
        const reviews = await fetchReviews(movieId);
        localStorage.setItem('selectedMovieReviews', JSON.stringify(reviews));
        window.location.href = '/details';
    };

    function displayWatchList() {
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
    }

    function displayWatchedList() {
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
    }

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

    window.addReview = function (event, movieId) {
        event.preventDefault();
        const reviewText = document.getElementById(`review-${movieId}`).value;
        if (!reviewText.trim()) {
            alert('Please enter a review before submitting.');
            return;
        }
        document.getElementById(`review-${movieId}`).value = '';

        const reviews = JSON.parse(localStorage.getItem('selectedMovieReviews')) || [];
        reviews.unshift(reviewText);
        localStorage.setItem('selectedMovieReviews', JSON.stringify(reviews));
        displayMovieReviews(movieId, reviews);

        alert('Review added!');
    };

    displaySearchResults(searchResultsData);
    displayWatchList();
    displayWatchedList();

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
            displayMovieDetails(movie);
        } catch (error) {
            console.error('Error fetching movie details:', error);
        }
    }
    if (window.location.pathname.includes('/details')) {
        const movieId = localStorage.getItem('selectedMovieId');
        if (movieId) {
            fetchMovieDetails(movieId).then(movie => {
                if (movie) {
                    document.getElementById('movie-title').textContent = movie.title;
                    document.getElementById('movie-poster').src = `https://image.tmdb.org/t/p/w200${movie.poster_path}`;
                    document.getElementById('movie-description').textContent = movie.overview;
                    document.getElementById('movie-year').textContent = movie.release_date.split('-')[0];
                    document.getElementById('movie-genre').textContent = movie.genres.map(genre => genre.name).join(', ');
                    document.getElementById('movie-actors').textContent = movie.credits.cast.map(actor => actor.name).join(', ');
                    document.getElementById('movie-director').textContent = movie.credits.crew.find(crew => crew.job === 'Director')?.name || 'N/A';
                    document.getElementById('movie-producer').textContent = movie.credits.crew.find(crew => crew.job === 'Producer')?.name || 'N/A';
                    document.getElementById('movie-budget').textContent = movie.budget ? `$${movie.budget.toLocaleString()}` : 'N/A';

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
                }
            }).catch(error => console.error('Error fetching movie details:', error));
        }
    }
});
