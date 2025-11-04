```java
[src]/
├── [Action]/
│   ├── (C) ActionManager
│   ├── (C) GButton
│   ├── (C) GButtonText
│   ├── (I) IGameUI
├── [FileIO]/
│   ├── (C) FileManager
│   ├── (I) IFile
│   └── (C) LogManager
├── [Logic]/
│   ├── (C) Login
│   └── (C) Core
├── [Render]/
│   ├── (C) IScreen
│   ├── (C) IScreenFactory
│   ├── (C) LoginScreen
│   ├── (C) ScreenManager
│   ├── (C) StartScreen
├── [Util]/
│   ├── (E) Phase
│   ├── (C) Constant
│   └── (E) Scenario
└── (M) Main
```

---

# [ FileIO ]

### IFile

- interface이다.
- 공통되는 기본 구조를 정의했다.

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
- 메인로직 구현 예정

Login

- user의 pw와 id를 hasMap으로 관리한다.

---

# [ Utile ]

Phase

- 이 이넘은 phase에 대한 값들을 가지고 있다.

Screen

- 화면의 이넘을 저장한다.

Constant

- 전역으로 사용할 상수값을 저장하는 클래스이다.

---

# [ Render ]

IScreen

- 기본적인 화면 클래스에 대한 interface

IScreenFactory

- IScreen을 상속받은 클래스 생성시 이 인터페이스를 상속받은 factory를 사용해야 함

ScreenManager

- 화면 관리하는 클래스

LoginScreen

- 로그인 화면

StartScreen

- 시작 화면

---

# 기본 작성 규칙

### ScreenManager

- 해당 클래스는 Screen 변경에 사용되는 클래스이다
- 화면을 추가하기 위해서는 반드시 core.java에서 인스턴스화 된 screenManager의 initScreen(Factory fac) 메소드를 통해 Screen을 추가해줘야 한다

```java
//예시
screenManager.initScreen(new StartScreenFactory());

...

class StartScreenFactory implements IScreenFactory
{
    public IScreen create()
    {
        return new StartScreen();
    }
}
```

- 또한 Screen 이넘에 스크린을 추가해줄 것
- 이동을 시키기 위해서는 아래 메소드를 활용하여야 한다

```java
screenManager.show(Screen.START);
```

### GButton, GButtonText

```java
//기본 구조
GButton(ImageIcon image, Runnable func)     // 이미지 아이콘과, 이벤트 발생 시 함수 입력
GButton(String imagePath, Runnable func)    // 이미지 아이콘의 path와 이벤트 발생 시 함수 입력
GButtonText(String text, Runnable func)     // 텍스트 출력. JButton과 동일 하나, 이벤트 발생 시 함수 입력을 넘겨줌

public void setBounds(int x, int y);        // 위치 조정
public void setScale(float scale);          // 일정 비율로 이미지 스케일 조정
public ImageIcon getImageIcon();            // 사용된 이미지 반환
```

- 해당 클래스 사용 시 매개변수로 넘겨줄 Runnable 함수를 따로 private으로 생성하여 넘겨줄 것

```java
//예시
GButtonText loginButton = new GButtonText("확인", () -> {loginButtonFunc(manager);});

private void loginButtonFunc(ScreenManager manager) { ... }
```

- 단, 한줄일 경우 따로 작성해주지 않고 바로 익명함수로 넘겨줘도 됨

```java
GButtonText backButton = new GButtonText("뒤로가기", () -> {manager.show(Screen.START);});
```