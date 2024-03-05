KT_DIR := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
RS_DIR := $(KT_DIR)/../rs

PLATFORMS := arm64-v8a=aarch64-linux-android x86_64=x86_64-linux-android

.PHONY: all
all: clean .WAIT install .WAIT build

.PHONY: build
build: install
	./gradlew :src:composeApp:build

.PHONY: clean
clean: clean_ffi clean_lib
	rm --force --recursive "./.gradle/"
	rm --force --recursive "./build/"
	rm --force --recursive "./gradle/build-logic/.gradle/"
	rm --force --recursive "./gradle/build-logic/convention/build/"
	rm --force --recursive "./src/composeApp/.gradle/"
	rm --force --recursive "./src/composeApp/build/"

.PHONY: clean_ffi
clean_ffi:
	rm --force "$(KT_DIR)/src/composeApp/src/commonMain/kotlin/uniffi"

.PHONY: clean_lib
clean_lib:
	rm --force --recursive "$(KT_DIR)/src/composeApp/src/androidMain/jniLibs/"

.PHONY: docker
docker:
	docker buildx build . \
		--build-context rs="../rs/" \
		--file "./docker/Dockerfile" \
		--output "dest=./,type=local" \
		--tag "ghcr.io/fruzitent/my_music_tool-kt:latest" \
		--target "artifact" \
		;

.PHONY: install
install: install_ffi install_lib

.PHONY: install_ffi
install_ffi: $(KT_DIR)/src/composeApp/src/commonMain/kotlin/uniffi/

$(KT_DIR)/src/composeApp/src/commonMain/kotlin/uniffi/: $(RS_DIR)/out/uniffi/
	mkdir --parent      "$(KT_DIR)/src/composeApp/src/commonMain/kotlin/"
	cp --recursive "$<" "$(KT_DIR)/src/composeApp/src/commonMain/kotlin/"

# TODO: statically dispatch platform
# .PHONY: install_lib
# install_lib:
# 	for platform in $(PLATFORMS); do \
# 		abi=$${platform%%=*};  \
# 		target=$${platform##*=}; \
# 		$(MAKE) $(KT_DIR)/src/composeApp/src/androidMain/jniLibs/$$abi/libspotify.so ABI=$$abi TARGET=$$target; \
# 	done

# $(KT_DIR)/src/composeApp/src/androidMain/jniLibs/$(ABI)/libspotify.so: $(RS_DIR)/target/$(TARGET)/release/libspotify.so
# 	mkdir --parent "$(KT_DIR)/src/composeApp/src/androidMain/jniLibs/$(ABI)/"
# 	cp "$<" "$@"

.PHONY: install_lib
install_lib: $(KT_DIR)/src/composeApp/src/androidMain/jniLibs/arm64-v8a/libspotify.so $(KT_DIR)/src/composeApp/src/androidMain/jniLibs/x86_64/libspotify.so

$(KT_DIR)/src/composeApp/src/androidMain/jniLibs/arm64-v8a/libspotify.so: $(RS_DIR)/target/aarch64-linux-android/release/libspotify.so
	mkdir --parent "$(KT_DIR)/src/composeApp/src/androidMain/jniLibs/arm64-v8a/"
	cp "$<" "$@"

$(KT_DIR)/src/composeApp/src/androidMain/jniLibs/x86_64/libspotify.so: $(RS_DIR)/target/x86_64-linux-android/release/libspotify.so
	mkdir --parent "$(KT_DIR)/src/composeApp/src/androidMain/jniLibs/x86_64/"
	cp "$<" "$@"
