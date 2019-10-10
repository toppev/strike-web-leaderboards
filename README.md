# strike-web-leaderboards
A simple but over-engineered StrikePractice web leaderboards written in Java using Vaadin Flow.

**Warning:** I'm not an expert

## Testing
- Clone the repository with `git clone https://github.com/toppev/strike-web-leaderboards.git`
- Type `mvn jetty:run` and head to http://localhost:8080
- Use `mvn clean package -Pproduction` for a production build

## Installation

#### With Tomcat
1. Install [apache tomcat](https://tomcat.apache.org/)
2. Download the .war file from [releases](https://github.com/toppev/strike-web-leaderboards/releases) (or compile it)
3. Create a `setenv.sh` (Windows `setenv.bat`) script in `CATALINA_HOME/bin` or `CATALINA_BASE/bin` and make it executable.
This is where you can configure the [variables](#variables).
4. Set the variables
     - Set the environment variables directly in the script. For example,
          - Linux: `export STRIKE_WEB_PASSWORD=mypass`
          - Windows `set STRIKE_WEB_PASSWORD=mypass`
     - Alternatively, you can add the Java properties in `CATALINA_OPTS` environment variable. For example,
          - Linux `export CATALINA_OPTS="$CATALINA_OPTS -Ddatabase.password=mysecretpassword69"`
          - Windows `set CATALINA_OPTS="%CATALINA_OPTS% -Ddatabase.password=mysecretpassword69"`
5. [Deploy](https://tomcat.apache.org/tomcat-9.0-doc/appdev/deployment.html) the .war file



### Variables
There are a few variables you can set as environment variables or as Java properties specified with a `-D`. If both are set environment variables are preferred.
These variables are used to connect to the MySQL database.

| Environment Variable  | Java Property | Description |
| ------------- | ----------- | ------------- |
| `STRIKE_WEB_HOST` | `-Ddatabase.host=localhost` | the host, by default localhost |
| `STRIKE_WEB_PORT` | `-Ddatabase.port=3306` | the port, by default 3306 |
| `STRIKE_WEB_DATABASE` | `-Ddatabase.name=strikepractice` | the database name, by default 'strikepractice' |
| `STRIKE_WEB_USER` | `-Ddatabase.user=someuser` | the user, 'root' by default, recommended to change it |
| `STRIKE_WEB_PASSWORD` | `-Ddatabase.password=mypass` | the password, 'password123' by default, change it |
