import React, { useState } from "react";

function App() {
  const [longUrl, setLongUrl] = useState("");
  const [shortUrl, setShortUrl] = useState("");
  const [shortInput, setShortInput] = useState("");
  const [expandedUrl, setExpandedUrl] = useState("");
  const [nbClicks, setNbClicks] = useState(0);
  const [listUrl, setListUrl] = useState([]);
  const [showAll, setShowAll] = useState(false);

  const handleShow = async () => {
    if (!showAll) {
      const res = await fetch("/api/showall", {
        method: "GET",
        headers: { "Content-Type": "application/json" },
      });
      const data = await res.json();
      console.log("DATA:", data); // ← vérifie si c’est un tableau
      setListUrl(data);
    }

    setShowAll(!showAll);
  };

  const handleShorten = async () => {
    const res = await fetch("/api/shorten", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ url: longUrl }),
    });
    const data = await res.json();
    setShortUrl(data.shortUrl);
  };

  const handleExpand = async () => {
    const res = await fetch(`/api/expand/${shortInput}`, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    });
    const data = await res.json();
    setExpandedUrl(data.longUrl); // adapter selon ta réponse backend
    setNbClicks(data.hits.length);
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Raccourcir une URL</h2>
      <input
        type="text"
        placeholder="Entrez URL longue"
        value={longUrl}
        onChange={(e) => setLongUrl(e.target.value)}
        style={{ width: "400px" }}
      />
      <button onClick={handleShorten}>Raccourcir</button>
      {shortUrl && <p>URL courte : {shortUrl}</p>}

      <hr />

      <h2>Développer une URL courte</h2>
      <input
        type="text"
        placeholder="Entrez URL courte"
        value={shortInput}
        onChange={(e) => setShortInput(e.target.value)}
        style={{ width: "400px" }}
      />
      <button onClick={handleExpand}>Développer</button>
      {expandedUrl && (
        <>
          <p>URL longue : {expandedUrl}</p>
          <p>Nombre de clicks: {nbClicks}</p>
        </>
      )}

      <hr />

      <h2>Liste des URLS</h2>

      <button onClick={handleShow}>Liste</button>
      {showAll &&
        listUrl.map((item, index) => (
          <div key={index}>
            <p>
              Long: {item.longUrl}
              <br />
              Court: {item.shortUrl} <br />
              Nombre de clics: {item.totalClicks}
            </p>
          </div>
        ))}
    </div>
  );
}

export default App;
