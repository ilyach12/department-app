# Dockerized department app
<p>Web application.<br>
<p>For build and launch project you need installed Apache maven, Java 8 and Docker.</p>

## Build
<ul>
    <li>Download source code from repository</li>
    <li>Move into root folder and run <code>build.sh</code> for build jar files</li>
    <li>Build docker images:
        <ul>
            <li>
                <code>
                    cd databse && \<br>
                    docker build -t database . && \<br>
                    cd ..
                </code>
            </li>
            <li>
                <code>
                    cd rest && \<br>
                    docker build -t server . && \<br>
                    cd ..
                </code>
            </li>
            <li>
                <code>
                    cd client && \<br>
                    docker build -t client . && \<br>
                    cd ..
                </code>
            </li>
        </ul>
    </li>
    <li>Then run docker containers:
        <ul>
            <li>
                Build database container: 
                <code>docker run -d --rm --name db -p 1521:1521 -p 81:81 database</code>
            </li>
            <li>Move <code>https://localhost:81</code> and configure db user login and password as `root`</li>
            <li>
                Build server container: 
                <code>docker run -d --rm --name rest -p 8080:8080 server bash -c "java -jar rest-0.0.1-SNAPSHOT.jar"</code>
            </li>
            <li>
                Build client container: 
                <code>docker run -d --rm --name web -p 3000:3000 client bash -c "cd web-app && npm install && npm start"</code>
            </li>
        </ul>
    </li>
    <li>Use application on next links:
        <ul>
            <li><code>http://localhost:3000/departments</code></li>
            <li><code>http://localhost:3000/departmentsWithEmployees</code></li>
            <li><code>http://localhost:3000/employees</code></li>
        </ul> 
    </li>
</ul>


