import { defineConfig } from 'vite';
import { fileURLToPath } from 'node:url';

export default defineConfig({
  root: 'src',
  server: {
    // En dev, redirige les appels API vers Spring Boot : même origine côté navigateur, donc pas de CORS
    proxy: {
      '/api': 'http://localhost:8080',
    },
  },
  build: {
    // Chemin relatif au dossier frontend/ (résolu indépendamment de `root`)
    outDir: fileURLToPath(new URL('../src/main/resources/static', import.meta.url)),
    emptyOutDir: true,
  },
});
