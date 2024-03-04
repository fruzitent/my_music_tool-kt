.PHONY: all build clean clean_ffi clean_lib install install_ffi install_lib

KT_DIR := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
RS_DIR := $(KT_DIR)/../rs

PLATFORMS := arm64-v8a=aarch64-linux-android x86_64=x86_64-linux-android

all: clean .WAIT install .WAIT build

build: install
	./gradlew :src:composeApp:build

clean: clean_ffi clean_lib
	rm --force --recursive "./.gradle/"
	rm --force --recursive "./build/"
	rm --force --recursive "./gradle/build-logic/.gradle/"
	rm --force --recursive "./gradle/build-logic/convention/build/"
	rm --force --recursive "./src/composeApp/.gradle/"
	rm --force --recursive "./src/composeApp/build/"

clean_ffi:
	rm --force "$(KT_DIR)/src/composeApp/src/commonMain/kotlin/uniffi"

clean_lib:
	rm --force --recursive "$(KT_DIR)/src/composeApp/src/androidMain/jniLibs/"

install: install_ffi install_lib

install_ffi: $(KT_DIR)/src/composeApp/src/commonMain/kotlin/uniffi/

$(KT_DIR)/src/composeApp/src/commonMain/kotlin/uniffi/: $(RS_DIR)/out/uniffi/
	mkdir --parent                                "$(KT_DIR)/src/composeApp/src/commonMain/kotlin/"
	ln --force --symbolic "$(RS_DIR)/out/uniffi/" "$(KT_DIR)/src/composeApp/src/commonMain/kotlin/"


# TODO: statically dispatch platform
# install_lib:
# 	for platform in $(PLATFORMS); do \
# 		abi=$${platform%%=*};  \
# 		target=$${platform##*=}; \
# 		$(MAKE) $(KT_DIR)/src/composeApp/src/androidMain/jniLibs/$$abi/libspotify.so ABI=$$abi TARGET=$$target; \
# 	done

# $(KT_DIR)/src/composeApp/src/androidMain/jniLibs/$(ABI)/libspotify.so: $(RS_DIR)/target/$(TARGET)/release/libspotify.so
# 	mkdir --parent "$(KT_DIR)/src/composeApp/src/androidMain/jniLibs/$(ABI)/"
# 	ln --force --symbolic "$<" "$@"

install_lib: $(KT_DIR)/src/composeApp/src/androidMain/jniLibs/arm64-v8a/libspotify.so $(KT_DIR)/src/composeApp/src/androidMain/jniLibs/x86_64/libspotify.so

$(KT_DIR)/src/composeApp/src/androidMain/jniLibs/arm64-v8a/libspotify.so: $(RS_DIR)/target/aarch64-linux-android/release/libspotify.so
	mkdir --parent "$(KT_DIR)/src/composeApp/src/androidMain/jniLibs/arm64-v8a/"
	ln --force --symbolic "$<" "$@"

$(KT_DIR)/src/composeApp/src/androidMain/jniLibs/x86_64/libspotify.so: $(RS_DIR)/target/x86_64-linux-android/release/libspotify.so
	mkdir --parent "$(KT_DIR)/src/composeApp/src/androidMain/jniLibs/x86_64/"
	ln --force --symbolic "$<" "$@"
