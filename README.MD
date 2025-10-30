# Prototype 기본 구성

---

```java
[src]/
├── [FileIO]/
│   ├── (C) Clue
│   ├── (C) FileManager
│   ├── (I) IFile
│   ├── (C) Line
│   └── (C) LogManager
├── [Logic]/
│   └── (C) Core
├── [Render]/
│   └── (C) RenderManager
├── [Util]/
│   ├── (E) Phase
│   └── (E) Scenario
└── (M) Main
```

---

# [ FileIO ]

### IFile

- interface이다.
- 공통되는 기본 구조를 정의했다.

### Clue

- 단서를 관리하는 클래스이다.
- index를 기반으로 단서를 가져온다.

### Line

- 대사를 관리하는 클래스이다.
- index를 기반으로 단서를 가져온다.

### LogManager

- log를 기록하는 클래스이다.
- 각 phase마다 각 캐릭터의 대사와 단서들의 log를 기록한다.
- 이 클래스를 기반으로 backup log들을 가져올 수 있다.

### FileManager

- 이 클래스는 clue, line, logManager를 관리한다.
- 이 클래스를 통해 /Logic/Core 에 필요한 데이터를 넘긴다.

---

# [ Logic ]

Core

- 이 클래스는 다른 package들을 관리한다.
- init은 다른 클래스들을 초기화하는 기능을 담당한다.
- run은 실질적인 동작이 돌아가는 함수이다.
- endCredit 게임이 끝났을 때 실행되는 함수이다.

---

# [ Utile ]

Phase

- 이 이넘은 phase에 대한 값들을 가지고 있다.

Scenario

- 이 이넘은 각 시나이로 마다의 값들을 가지고 있다.
    - 김재민 대사 1, 김재민 대사 2, 왕석빈 대사 1, 왕석빈 대사 2 ~~ 을 저장한다.

---

# [ Render ]

RenderManager

- 이 클래스에서는 swing을 사용해 화면 렌더링을 담당한다.
