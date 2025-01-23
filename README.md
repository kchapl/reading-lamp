# Book Reading History Web App

This is a Scala Play web application for recording book reading history. The web app allows users to enter an ISBN and a date that the book was begun, and it displays a list of books read. The site supports different users signing in using Google Sign-In.

## Setup

1. Clone the repository:
   ```
   git clone https://github.com/githubnext/workspace-blank.git
   cd workspace-blank
   ```

2. Install the necessary dependencies:
   ```
   sbt update
   ```

3. Run the application:
   ```
   sbt run
   ```

4. Open your browser and navigate to `http://localhost:9000` to see the application in action.

## Deployment to Render.com

1. Create a new web service on Render.com and connect it to your GitHub repository.

2. Set the build and start commands:
   - Build Command: `sbt stage`
   - Start Command: `target/universal/stage/bin/workspace-blank -Dplay.http.secret.key=your_secret_key`

3. Configure environment variables for Google Sign-In and database connection in the Render.com dashboard.

4. Deploy the application and access it via the provided URL.

## Environment variables

The following environment variables need to be set for the application to run:

* `GOOGLE_CLIENT_ID`: Your Google client ID.
* `GOOGLE_CLIENT_SECRET`: Your Google client secret.
* `GOOGLE_REDIRECT_URI`: Your Google redirect URI.
* `DB_URL`: The URL of your database.
* `DB_USERNAME`: The username for your database.
* `DB_PASSWORD`: The password for your database.

You can set these environment variables in your deployment environment or in a `.env` file for local development.
