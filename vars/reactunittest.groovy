def call(String Report) {
  sh """
      npm install --save-dev jest@23.6.0 babel-jest@23.6.0
      mkdir -p ${Report}
      npm test | tee ${Report}/unit.test.html
  """
}
