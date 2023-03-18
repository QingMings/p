import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  build: {
    // 输出打包位置
    outDir: '../src/main/resources/webroot',
    assetsRoot:'../src/main/resources/webroot',
    // 下面这种写法指定静态文件 js/css夹等与index平级
    // 指定为'/' 会打包会出现错误，最高只能指定到当前目录'./'  指定目录不存在会自动创建
    // 也就是说js,css文件夹的路径其实是上面的: '../dist' + assetsSubDirectory
    assetsSubDirectory: 'static',
    // index.html中引用资源的前缀
    // 相当于static/js/app.js的前缀 eg： ./static/js...     /static/js.....
    assetsPublicPath: './',
  }
})
