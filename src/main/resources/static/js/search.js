    const searchInput = document.getElementById('searchInput');
    const resultsList = document.getElementById('resultsList');

    let timeout = null; // чтобы делать небольшую задержку

    searchInput.addEventListener('input', () => {
    const query = searchInput.value.trim();

    // Запускаем поиск только если введено >= 3 символов
    if(query.length < 3){
    resultsList.innerHTML = ""; // очищаем результаты
    return;
}

    // Небольшая задержка, чтобы не спамить сервер
    clearTimeout(timeout);
    timeout = setTimeout(() => {
    fetch(`/dictionary/search?query=${query}`)
    .then(response => response.json())
    .then(data => {
    resultsList.innerHTML = "";
    data.forEach(word => {
    const li = document.createElement('li');
    li.classList.add('list-group-item');
    li.textContent = `${word.word_pl} — ${word.word_en}`;
    li.addEventListener('click', () => {
    alert("Вы выбрали: " + word.word_pl + " / " + word.word_en);
});
    resultsList.appendChild(li);
});
});
}, 300); // задержка 300мс
});

