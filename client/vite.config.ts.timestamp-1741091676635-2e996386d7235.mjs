// vite.config.ts
import path from "path";
import { defineConfig } from "file:///D:/Project%20Spring%20Boot/fpl-scrum-helper/client/node_modules/vite/dist/node/index.js";
import vue from "file:///D:/Project%20Spring%20Boot/fpl-scrum-helper/client/node_modules/@vitejs/plugin-vue/dist/index.mjs";
var __vite_injected_original_dirname = "D:\\Project Spring Boot\\fpl-scrum-helper\\client";
var vite_config_default = defineConfig({
  define: {
    global: "globalThis"
  },
  plugins: [vue()],
  server: {
    port: 6789,
    strictPort: false
  },
  css: {
    preprocessorOptions: {
      scss: {
        api: "modern-compiler"
      }
    }
  },
  resolve: {
    alias: {
      "@": path.resolve(__vite_injected_original_dirname, "./src")
    }
  }
});
export {
  vite_config_default as default
};
