banestein
=========

Yandex Translate API key:
```
trnsl.1.1.20140824T225426Z.96c74a6bb1ded30a.971772c586748b28435f70cfaa6cb938071f8b30
```

Almagu API:
1. Get a PhraseHash:
```
http://www.almagu5.com/webreader?callback=?&cid=<arbitrary id; default: "CID">&markup=<text to be spoken>
```

Returns a JSON obj of format:
```
?({"Status":0,"PhraseHash":"524904FE6C3E98F7F08265052189586F98398D30"});
```

2. Download MP3 file using PhraseHash, speed, and voice selection:
```
http://www.almagu5.com/webreader/audio/<id>_<PhraseHash>_<f or s>_<Gilad or Sivan>.mp3?<new Date().toTime()>
```
