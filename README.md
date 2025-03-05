# Work Tracker Application

## Overview
The Work Tracker application is designed to help users log and visualize their work over set periods of time. It promotes productivity through task management and visualization techniques, including the Pomodoro technique. The application features a muted earth tone palette to create a calming user experience.

## Features
- **Task Management**: Add, update, and delete tasks easily.
- **Work Session Tracking**: Log work sessions and visualize progress.
- **Email Reports**: Generate and send reports via email to share work done.
- **Customizable Settings**: Adjust thresholds for what is considered good work and notification preferences.
- **Celebratory Animations**: Enjoy animations and messages when work goals are achieved.
- **Visualizations**: View progress through charts and task boxes.

## Project Structure
```
work-tracker/
├── README.md
├── package.json
├── public/
│   ├── index.html
│   ├── favicon.ico
│   └── assets/
│       ├── images/
│       └── fonts/
└── src/
   ├── App.js
   ├── index.js
   ├── components/
   │   ├── TaskBoard/
   │   ├── Reports/
   │   ├── Settings/
   │   └── common/
   │       ├── TaskBox.js
   │       ├── ProgressChart.js
   │       └── CelebrationAnimation.js
   ├── services/
   │   ├── EmailService.js
   │   ├── GraphGeneratorService.js
   │   └── StatisticsService.js
   ├── models/
   │   ├── Task.js
   │   ├── TimeBlock.js
   │   ├── WorkSession.js
   │   └── UserSettings.js
   ├── utils/
   │   ├── DateTimeUtil.js
   │   └── ColorTheme.js
   ├── styles/
   │   ├── theme.css
   │   └── components/
   └── config/
      └── config.js
```

## Getting Started
1. **Clone the Repository**: 
   ```
   git clone <repository-url>
   cd work-tracker
   ```

2. **Install Dependencies**: 
   ```
   npm install
   ```

3. **Run the Application**: 
   ```
   npm start
   ```

## Usage
- **Adding Tasks**: Navigate to the Task Board and use the provided interface to add new tasks.
- **Logging Work Sessions**: Start a work session and log tasks completed during that time.
- **Generating Reports**: Access the Report View to generate and send reports via email.
- **Adjusting Settings**: Modify your work thresholds and notification preferences in the Settings View.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any suggestions or improvements.

## Future Fixes
   - Dedicated login GUI page
   - Better choice throws ("report sent" despite choosing not to)
   - Right side of GUI
      - Finding where images are kept or sent, perhaps make those an option
      - Fix time sent

## License
This project is licensed under the MIT License. See the LICENSE file for details.
