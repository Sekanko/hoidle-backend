# 🎖️ ️Welcome to Hoidle 🎖️

**Hoidle** is my little project inspired by **Loldle**, 
but instead of using League of Legends champions, 
it uses **Hearts of Iron IV (HOI4) countries**.

## How does it work? 🤔
- You enter the name of any country in HOI4.
- You will be given a series of clues related to a country.
- Your goal is to guess the country based on these clues.

**_Now feel free to [check it out](https://hoidle.netlify.app/)!_**

## ⚙️ Technicals 

### 🛠️ Technologies Used:
- Frontend: HTML, LESS, JavaScript
- Backend: Java 17, Spring Boot, PostgreSQL, MySQL (used initially)
- Additional: IntelliJ IDEA, Gradle, MySQL Workbench, Docker, Git



### 🧱Build Instructions

1. Clone the repository
2. In the Client module you should see constants.js

`client/js/common/constants.js`

where are the lines

```javascript
const http = 'http://localhost:8080/';
// const http = 'https://hoidle.onrender.com/';
```
If you want to test it using my hosted backend, change the code above to this:
```javascript
// const http = 'http://localhost:8080/';
const http = 'https://hoidle.onrender.com/';
```
and jump to the `🔗 Application Page` part.

**Although be aware that it is slow because I only use free
services for my hobby. Also, my host runs the latest stable version**

Otherwise, set these constants to point to localhost 
(as it is in example) and follow down the instructions.

3. Create a local PostgreSQL database
4. Set the following environment variables:

```
DATABASE_USER=your_username
DATABASE_PASSWORD=your_password
DATABASE_URL=your_database_url
JWT_SECRET=your super secret key - it has to be strong enough
```

> 💡 Example:
> ```
> DATABASE_USER=data_manager
> DATABASE_PASSWORD=123
> DATABASE_URL=postgresql://host.docker.internal:5432/my_db
> JWT_SECRET=OMzOz0AdL0021216v/GB4Me7RQPaoeztFqxIdyF8/eK+avQ=
> ```

5. Run HoidleApplication
6. Load data from the file:

To trigger a data load for the database, send a `POST` request to:
   `http://localhost:8080/admin/from/file/csv/countries.csv`

_Thanks to my friend Filip for creating and sharing this file_

### 🔗 Application page

Please open `index.html` from **Client** module and enjoy the game!

## 💬 My comment
**Hoidle** is an idea that my friend Filip and I came up with
after playing **Loldle** a lot.

It's my first project that I made for myself, and I'm really happy with the
result. After completing the first game mode, I decided to move on and learn
more technologies to improve quality of this project. I'm going to leave it as
it is and make a new one from scratch.

## 🙏 Thank you for your time!
# hoidle-backend
