module.exports = {
  content: [
    "./src/main/resources/templates/**/*.html",
    "./src/main/resources/static/css/**/*.css",
    "./src/main/resources/static/**/*.js"
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#059669',
          dark: '#047857',
          light: '#10b981'
        },
        secondary: {
          DEFAULT: '#1d4ed8',
          dark: '#1e40af',
          light: '#3b82f6'
        },
        hero: {
          DEFAULT: '#1e4080', // A nice deep blue
          dark: '#1a3668'
        },
        background: {
          DEFAULT: '#1e4080',
          dark: '#1a3668'
        }
      }
    },
  },
  plugins: [
    require('@tailwindcss/forms')
  ],
}