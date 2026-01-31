<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Traducteur Anglais -> Darija</title>
    <style>
        body { font-family: sans-serif; max-width: 800px; margin: 2rem auto; padding: 0 1rem; }
        .container { display: flex; gap: 20px; }
        .box { flex: 1; }
        textarea { width: 100%; height: 150px; padding: 10px; border-radius: 5px; border: 1px solid #ccc; }
        button { background-color: #007bff; color: white; padding: 10px 20px; border: none; cursor: pointer; border-radius: 5px; margin-top: 10px; }
        button:hover { background-color: #0056b3; }
        .result { background-color: #f8f9fa; padding: 15px; border-radius: 5px; border: 1px solid #ddd; min-height: 120px; }
    </style>
</head>
<body>
    <h1>🇬🇧 English to 🇲🇦 Darija Translator</h1>
    
    <form action="index.php" method="POST">
        <div class="container">
            <div class="box">
                <h3>Anglais (English)</h3>
                <textarea name="text" placeholder="Enter text here..."><?php echo isset($_POST['text']) ? htmlspecialchars($_POST['text']) : ''; ?></textarea>
            </div>
            <div class="box">
                <h3>Darija (Traduction)</h3>
                <div class="result">
                    <?php 
                        if ($_SERVER['REQUEST_METHOD'] === 'POST' && !empty($_POST['text'])) {
                            include 'logic.php'; // On appelle la logique ici
                        }
                    ?>
                </div>
            </div>
        </div>
        <button type="submit">Traduire / Translate</button>
    </form>
</body>
</html>