document.getElementById('translateBtn').addEventListener('click', async () => {
    const input = document.getElementById('inputText').value.trim();
    const resultDiv = document.getElementById('result');

    if (!input) {
        resultDiv.textContent = "Veuillez entrer du texte.";
        return;
    }

    resultDiv.textContent = "Traduction en cours...";

    try {
        const response = await fetch('http://localhost:8081/api/translate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa('admin:admin')   // ← identifiants exacts du filtre
            },
            body: JSON.stringify({ text: input })
        });

        if (!response.ok) {
            const errText = await response.text();
            throw new Error(`Erreur ${response.status}: ${errText}`);
        }

        const data = await response.json();
        resultDiv.textContent = data.translation || "Traduction reçue";
    } catch (error) {
        console.error(error);
        resultDiv.textContent = "Erreur : " + error.message;
    }
});