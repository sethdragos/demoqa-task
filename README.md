# demo-qa-project

To run the tests with Maven and aggregate test results run the following command in the project root directory:

mvn -U clean test -Dwebdriver.driver=chrome -Dmaven.test.failure.ignore=true serenity:aggregate


After the test runs open target/site/serenity/index.html in a browser to check the results.