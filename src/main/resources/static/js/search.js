document.getElementById('searchInput').addEventListener('input', function() {
    const query = this.value.trim();
    const resultsDiv = document.getElementById('resultsList');

    if (query.length < 3) {
        resultsDiv.innerHTML = '';
        return;
    }

    fetch(`/search?q=${encodeURIComponent(query)}`)
        .then(response => response.json())
        .then(data => {
            resultsDiv.innerHTML = '';

            if (data.length === 0) {
                const noResult = document.createElement('li');
                noResult.classList.add('list-group-item');
                noResult.textContent = 'Brak wyników.';
                resultsDiv.appendChild(noResult);
                return;
            }

            data.forEach(word => {
                const item = document.createElement('li');
                item.classList.add('list-group-item');
                item.id = word.id;

                const link = document.createElement('a');
                link.href = `/${word.word}`;

                link.style.textDecoration = 'none';
                link.style.color = 'inherit';
                link.style.display = 'block';

                link.innerHTML = `<b> ${word.word} <br/><small>${word.description ?? ''}</small>`;

                item.appendChild(link);

                resultsDiv.appendChild(item);
            });
        })
        .catch(err => console.error('Błąd wyszukiwania:', err));
});
