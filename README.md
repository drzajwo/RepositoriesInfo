# RepositoriesInfo
Get list of specific repositories

![Device screenshot](https://github.com/drzajwo/RepositoriesInfo/blob/master/device-2019-01-11-133927.png)


# Testing
Application displays only single view with search bar and list of results.
Results are displayed in RecyclerView which is not easy to test in case of testing every single cell of data.
Most of app actions are API calls to GitHub server and displaying results. So most of the testing should check integration with GitHub likewise UI testing if every element is displayed properly.
Good approach for many tests would be implementing mock server (i.e. Mockito) but it's still on TODO list.

## App AC:
- Search should be triggered automatically after user enter 3 characters in search input field
- Search results should be paged (with page size of 10 )
- If a repository has a wiki page then the cell for this repository should have darker background
- Repository name should be displayed in the cell (as shown in the image below)

## Unit Tests
Not Done - It would require mock server to get always same data in return from API.

## Integration Tests
In case of this application the only integration is between application under tests and content provider which is GitHub API. Some tests are present in project but they are using hardcoded data which is vunerable in case of changes in GitHub server.

List of tests in `com/test/denis/repositoriesinfo/APIGithubIntegrationTest.kt` path:
 - `githubApiCall_standardCallSize_isCorrect()`
 - `githubApiCall_nonExistingRepo_isEmpty()`
 - `githubApiCall_emptyQuery_isEmpty(`
 - `githubApiCall_zeroItemsPerPage_isEmpty()`
 - `githubApiCall_repoOverviewStructure_isCorrect()`
 - `githubApiCall_repoOwnerStructure_isCorrect()`

## UI Tests
There are some UI tests done in file at path: `com/test/denis/repositoriesinfo/spec/RepoListViewTest.kt`. Mostly we have to test scenarios:
- if elements are accessible
- if elements are displayed properly
- if view meets acceptance criteria
- how app behaves in possible user interactions


List of tests:
- `searchBarIsDisplayedTest` - passing - app displays necessary element
- `repoListIsDisplayedTest` - passing - app displays necessary element
- `autoUpdateAfter3CharsTest`- failing AC1 - auto update is not trigerred after typing 3 chars
- `autoUpdateAfter3CharsRetriggerTest` - failing AC1 - auto update is not trigerred after typing 3 chars
- `typeTextAndHitSearchTest` - passing - correct app beahaviour
- `repoListElementsCountScrollAndAgainTest` - passing - AC2 - app displays 10 elements per page, after scroll there is 20
- `failingSearchTest` - passing - AC2 - checks if on non existing search results are empty
- `itemWithWikiWhiteBackgroundTest` - failing - AC3 - not done due to complex testing of RecyclerView element
- `itemWithoutWikiDarkBackgroundTest`- failing - AC3 - not done due to complex testing of RecyclerView element
- `tetrisCompleteCellDataDisplayTest` - failing - AC4 - Checking some elements of cell if they are displayed properly, in app issue with displaying repo name


All tests were run against Android Emulator with API28 in AndroidStudio 3.5