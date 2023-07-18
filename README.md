## SetUp

Clone the repository from GitHub:

git clone https://github.com/Abuzar-Aslam/EgeniqSampleApp.git

Open the project in Android Studio.

Create a gradle.properties file in the project's root directory if it does not exist.

Add your API key from The Cat API in the gradle.properties file:

**Replace YOUR_API_KEY with your actual API key from the [https://thecatapi.com/]**
- apiAccessKey="YOUR_API_KEY"

**This API key is required to access the images of cat breeds from The Cat API.**

## Architecture

The app follows the Clean Architecture principles and is divided into three layers: data, domain, and presentation. Each layer has its own responsibilities and dependencies.

# Data Layer
The data layer is responsible for retrieving data from external sources, such as APIs or databases. It consists of the following components:

- DataSource: The DataSource interface defines the contract for retrieving data from the API. 
- BreedingPagingSource: The BreedPagingSource class implements the PagingSource interface and handles pagination and data loading from the API using dataSource

- Model: The BreedResponse and ImageResponse data classes represent the response models for a breed and an image, respectively.

- Repository: The BreedRepository interface defines the contract for accessing breeds data. The BreedRepositoryImpl class implements this interface and interacts with the BreedPagingSource to retrieve paginated breed data.

# Domain Layer

The domain layer contains the business logic and models of the application. It includes the following components:

- Model: The BreedResult and ImageResult data classes represent the domain models for a breed and an image, respectively.

- Use Case: The BreedUseCase class provides methods to retrieve a list of breeds from the data layer and maps the data models to the domain models.

# Presentation Layer
The presentation layer handles UI-related logic and user interactions. It uses Jetpack Compose for building the UI. Key components include:

- ViewModel: The MainViewModel class is responsible for managing the state and business logic of the main screen. It interacts with the BreedUseCase to retrieve breeds and handles UI actions.

- UI: The MainUI composable function represents the main screen of the app. It displays a list of cat breeds using the BetItem and displayBreedList composable functions. The UI state is managed by the MainViewModel, and error handling is done using the ErrorSnackbar composable.

# Dependencies
The app utilizes the following libraries and tools:

- Retrofit: For making API requests to The Cat API.
- Hilt: For dependency injection and managing app-level dependencies.
- Jetpack Compose: For building the user interface.
- Paging 3: For implementing pagination in the list of cat breeds.
- Coil: For loading and displaying images efficiently.
- ViewModel: For managing UI-related data and state.

- Conclusion
Egeniq Sample App is an example of a modern Android app following the Clean Architecture principles, utilizing Jetpack Compose for UI, and showcasing best practices in Android development. The app allows users to explore a list of cat breeds and view their images and temperaments.

# Future Improvements

- Add more test coverage which includes (Unit, UI)
- Save the data in local database to support offline usage of the application
- better data loading, first page data loading to splash screen for better user experience