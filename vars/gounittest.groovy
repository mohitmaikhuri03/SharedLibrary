def call(String reports) {
    sh """
        mkdir -p ${reports}
        go mod tidy
        go test ./... -v > ${reports}/unit-test-report.txt
    """
}

