# AI Skincare Advisor

![App Icon](app/src/main/res/drawable/ic_app_icon.png)

An intelligent Android application that provides personalized skincare advice using AI-powered image analysis. The app analyzes your skin condition through photos and provides customized recommendations for skincare routines and products.

## 🌟 Features

### 🔍 AI-Powered Skin Analysis
- **Smart Image Analysis**: Upload photos of your skin for AI-powered analysis
- **Comprehensive Results**: Get detailed insights about skin type, hydration, oil levels, and overall condition
- **Concern Detection**: Identify specific skin concerns like acne, dryness, aging signs, and more
- **Personalized Recommendations**: Receive tailored advice based on your unique skin analysis

### 💬 AI Chat Assistant
- **Interactive Skincare Consultation**: Chat with an AI assistant for personalized skincare advice
- **Real-time Responses**: Get instant answers to your skincare questions
- **Expert Knowledge**: Access professional-level skincare guidance powered by advanced AI

### 🧴 Product Recommendations
- **Curated Product Database**: Browse skincare products matched to your skin type
- **Smart Filtering**: Products recommended based on your skin analysis results
- **Direct Purchase Links**: Easy access to purchase recommended products
- **Category-based Organization**: Find products by cleanser, moisturizer, serum, and more

### 📅 Skincare Routine Management
- **Custom Routines**: Create and manage daily skincare routines
- **Step-by-step Guidance**: Follow structured routines with checkboxes for tracking
- **Progress Tracking**: Monitor your skincare journey over time

### 👤 User Management
- **Firebase Authentication**: Secure login with email/password and Google Sign-In
- **Profile Management**: Manage your personal information and preferences
- **Analysis History**: View past skin analysis results and track improvements
- **Dark/Light Theme**: Toggle between different app themes

### 🔒 Security & Privacy
- **Firebase Security**: Secure data storage with Firebase Firestore
- **reCAPTCHA Protection**: Enhanced security for user authentication
- **Local Data Storage**: Secure local storage for user preferences

## 🛠️ Technology Stack

### Frontend
- **Kotlin**: Modern Android development language
- **Jetpack Compose**: Modern Android UI toolkit
- **Material Design 3**: Google's latest design system
- **Navigation Compose**: Type-safe navigation
- **Coil**: Image loading library

### Backend & AI
- **Firebase Authentication**: User authentication and management
- **Firebase Firestore**: NoSQL cloud database
- **Azure AI**: Advanced AI models for skin analysis
- **Google Generative AI (Gemini)**: AI chat functionality
- **Firebase Crashlytics**: Crash reporting and analytics

### Architecture
- **MVVM Pattern**: Model-View-ViewModel architecture
- **Repository Pattern**: Clean data layer architecture
- **StateFlow**: Reactive state management
- **Coroutines**: Asynchronous programming

## 📱 Screenshots

_Screenshots will be added here showing the main features of the app_

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK 26 or higher
- Java 11 or higher
- Firebase project setup
- Azure AI credentials

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/duongdatdev/ai-skincare-advisor-app.git
   cd ai-skincare-advisor-app
   ```

2. **Setup Firebase**
   - Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Enable Authentication and Firestore Database
   - Download `google-services.json` and place it in the `app/` directory
   - Enable Google Sign-In in Authentication methods

3. **Configure API Keys**
   Create a `local.properties` file in the root directory with the following:
   ```properties
   # Azure AI Configuration
   apiAIToken=your_azure_ai_token_here
   azureAIEndpoint=your_azure_ai_endpoint_here
   
   # Google Services
   GOOGLE_WEB_CLIENT_ID=your_google_web_client_id_here
   ```

4. **Setup reCAPTCHA**
   - Get your reCAPTCHA site key from [Google reCAPTCHA](https://www.google.com/recaptcha/)
   - Update `recaptcha_site_key` in `app/src/main/res/values/strings.xml`

5. **Build and Run**
   ```bash
   chmod +x gradlew
   ./gradlew assembleDebug
   ```

### Configuration Details

#### Firebase Setup
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or use existing one
3. Add an Android app with package name: `com.proteam.aiskincareadvisor`
4. Download and add `google-services.json` to `app/` folder
5. Enable Authentication with Email/Password and Google Sign-In
6. Create Firestore Database in test mode

#### Azure AI Setup
1. Create an Azure AI service account
2. Get your API token and endpoint
3. Add them to `local.properties` file

#### Google Generative AI Setup
1. Get API key from [Google AI Studio](https://aistudio.google.com/)
2. Add it as `apiAIToken` in `local.properties`

## 📦 Project Structure

```
app/
├── src/main/java/com/proteam/aiskincareadvisor/
│   ├── data/
│   │   ├── api/           # AI client configurations
│   │   ├── auth/          # Firebase authentication
│   │   ├── firestore/     # Database operations
│   │   ├── model/         # Data models
│   │   ├── repository/    # Data repositories
│   │   └── viewmodel/     # ViewModels
│   ├── ui/
│   │   ├── components/    # Reusable UI components
│   │   ├── screens/       # Application screens
│   │   └── theme/         # App theming
│   └── MainActivity.kt    # Main activity
└── src/main/res/          # Resources (layouts, strings, etc.)
```

## 🔧 Key Components

### Data Models
- **SkinAnalysisResult**: Stores comprehensive skin analysis data
- **Product**: Represents skincare products with recommendations
- **RoutineStep**: Individual steps in skincare routines

### Main Screens
- **HomeScreen**: Dashboard with analysis results and product recommendations
- **AnalysisScreen**: Camera integration and skin analysis
- **ChatScreen**: AI chat assistant
- **RoutineScreen**: Skincare routine management
- **ProfileScreen**: User profile and settings

### AI Integration
- **AIClient**: Handles Azure AI and Google Generative AI connections
- **SkinAnalysisRepository**: Manages skin analysis operations
- **ChatRepository**: Manages AI chat functionality

## 🧪 Testing

Run tests using:
```bash
./gradlew test                # Unit tests
./gradlew connectedAndroidTest # Integration tests
```

## 📄 API Documentation

### Skin Analysis API
The app integrates with Azure AI and Google's Generative AI to provide:
- Image-based skin analysis
- Skin type detection
- Hydration and oil level assessment
- Personalized recommendations

### Firebase Integration
- **Authentication**: Email/password and Google Sign-In
- **Firestore**: Real-time database for user data and analysis history
- **Crashlytics**: Automatic crash reporting

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:
- Create an issue in this repository
- Contact: duongdat030@gmail.com

## 🙏 Acknowledgments

- Google Firebase for backend services
- Azure AI for advanced skin analysis capabilities
- Google Generative AI for chat functionality
- Jetpack Compose team for the modern UI toolkit
- Material Design team for design guidelines

---

**Note**: This app requires proper API keys and Firebase configuration to function correctly. Make sure to follow the setup instructions carefully.
