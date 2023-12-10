module.exports = {
  root: true,
  plugins: ['cypress', '@typescript-eslint'],
  env: {
    mocha: true,
    'cypress/globals': true,
  },
};
