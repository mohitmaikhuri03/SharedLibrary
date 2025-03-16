def call(String reports) {
    sh """
        mkdir -p ${reports}
        go test ./... -v > ${reports}/unit-test-report.txt
    """
}

