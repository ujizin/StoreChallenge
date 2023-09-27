## Via Verde Challenge

The project utilized Clean Architecture, MVVM pattern, and modern Android Jetpack libraries (MAD).
Modularization was not implemented due to time constraints, but the packages were structured as
corresponding modules.

<p align="center">
  <img src="https://github.com/ujizin/store-challenge/assets/51065868/598b9d49-558e-453f-9e02-f87c96f2f8eb" width="300"/>  
</p>

## 🏗 Stack

- Jetpack Compose
- Jetpack Compose Navigation
- Material 3 (Material You)
- Coroutines
- Paging 3
- MVVM
- Hilt
- Coil
- Retrofit
- Room
- MockK

## 📋 My Opinions

### Problems & solutions

At first, there were doubts about functionality 1, related to loading during application
initialization. Initially, ResultMediator was used, but when the application was restarted, there
was still a request at the end of pagination, which is common to check for more items remotely. To
resolve this, a call to fetch products was made during the application's initialization, and
ResultMediator was removed, preventing additional request calls during pagination. The version with
ResultMediator is still available in the 'feat/paging3-result-mediator' branch.

### Feedback

The challenge was simple and short, and I tried using Paging3 with Compose for the first time, which
was a pleasant experience
