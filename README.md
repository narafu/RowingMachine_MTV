# RowingMachine_MVP

1. aws - ec2 인스턴스 생성
2. aws - rds 생성
    1. rds 보안그룹 추가
        1. 인스턴스 보안그룹 id 추가
        2. 퍼블릭 허용(dbeaver 접속 위해서)
    2. datasource 설정(aws-rds 앤드포인트 이용)
        
        ```yaml
        # jdbc:mysql:// + 앤드포인트:포트/데이터베이스/autoReconnect=true 
        jdbc:mysql://rowingmachine.cakioqitx4ac.ap-northeast-2.rds.amazonaws.com:3306/rowing_db?autoReconnect=true
        ```
        
    3. dbeaver 구성(aws-rds 앤드포인트 이용)
3. ec-2 보안그룹 추가
    1. http, https, 8080포트 추가
4. ec2
    1. git 설치
        
        ```bash
        sudo yum install -y git
        ```
        
    2. mysql 설치
        
        ```bash
        sudo yum install mysql
        ```
        
    3. java 설치
        
        ```bash
        sudo amazon-linux-extras install java-openjdk11
        ```
        
5. 소스 빌드
    
    ```bash
    # home
    mkdir app
    cd app
    git clone [저장소] // git pull
    
    # mvnw 위치
    chmod +x mvnw // 권한 추가
    ./mvnw clean package // 빌드
    
    # targer 위치
    nohup java -jar [소스파일] & // 서버 실행
    ```
    
    - 빌드 오류가 많이 났다.
        - 중간에 패키지명을 바꿨었는데, application 패키지명과, applicationTest 패키지명이 달라서 오류가 계속 났다. 대소문자도 같아야 한다.
        - 그리고, 내장 톰켓 실행이 되지 않아서 또 오류가 발생했다. pom.xml에 dependency를 추가해주었다.
        - 또 오류가 났다. 검색해보니 포트를 바꾸라고 해서, 원래 80이었는데 8080으로 바꿔주니, 드디어, 빌드가 되었다.
    - 콘솔을 종료하니 서버가 종료되었다.
        - 백그라운드 실행
        
        ```bash
        nohup java -jar [소스파일] &
        ```
        
6. 프로세스 확인
    
    ```bash
    ps -ef | grep java
    kill -9 PID
    ```
