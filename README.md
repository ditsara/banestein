banestein
=========

Yandex Translate API key:
```
trnsl.1.1.20140824T225426Z.96c74a6bb1ded30a.971772c586748b28435f70cfaa6cb938071f8b30
```

Almagu API:

1. Get a PhraseHash:
  ```
  http://www.almagu5.com/webreader?callback=?&cid=<ID>&markup=<text>
  ```
  The ID default is "CID", but seems to be arbitrary and only used to identify
  the phrase in part #2.
  
  Returns a JSON obj of format:
  ```
  ?({"Status":0,"PhraseHash":"524904FE6C3E98F7F08265052189586F98398D30"});
  ```
  Notice that this is improperly formatted, and requires some string manipulation
  to get it to parse as valid JSON.

2. Download MP3 file using PhraseHash, speed, and voice selection:
  ```
  http://www.almagu5.com/webreader/audio/<id>_<PhraseHash>_<f / s>_<Gilad / Sivan>.mp3?<timestamp>
  ```
  Gilad is a male voice, Sivan is a female voice. The timestamp can be created
  with `new Date().toTime()`, but doesn't seem to be necessary. It's probably
  only for logging purposes.
