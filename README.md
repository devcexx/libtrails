# Libtrails
Little trails &amp; particles library for Spigot.

This library contains utilities oriented to simplify the creation of trails and effects with particles in Spigot. This includes mathematical functions to work with vectors in the three-dimensional space and some kawaii predefined trails such as helix generators, sinusoidal waves, some shapes generators and even a image renderer with particles.

## Usage

First things first, you have to be clear about that **THIS IS NOT A SPIGOT PLUGIN**. This is just a utility library and, therefore, if you want to use it, you should shade it into your plugin final jar.

As I don't provide any Maven repository for this library, if you are using GIT as VCS, maybe could be a good idea to use this project as a submodule, to make easier the compilation process, and to be sure that you're code is always up-to-date with the project.

This Maven project is divided in two main reactors: the library itself and an example reactor, that contains example plugins of the usage of this library. This examples are fully functional plugins that can be directly loaded by spigot to be tested, and can help you to understood (unless until I write a better README file). If you want to compile just the library, you have to activate the profile `build-library` of the Maven project as follows:

```mvn clean install -Pbuild-library```

In the other hand, if you want to compile only the examples, you have to active the profile `build-examples` of the project:

```mvn clean install -Pbuild-examples```

## Compatibility

This library is supposed to work in Spigot 1.8 - 1.11 inclusive, but it has been only tested in Spigot 1.8. Also, it uses some deprecated API in the Spigot 1.11 API Specification to allow the library to be compatible with previous versions of it.

## License

This software is under the GNU GPL v3 license. This mean, in a nutshell, that you can freely use and distribute open source software that uses this library, but you cannot use it for privative projects. For more details about this license, please refer to the file LICENSE present in this repository.
