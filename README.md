### Spring Boot app which using Bouncy Castle library to encrypt and decrypt files.

Instructions to **generate public and secret key** to encrypt/decrypt files or messages:

- install "GnuPG (aka PGP/GPG)":

  	$ sudo apt-get install gnupg 
  	$ sudo apt-get install rng-tools 
  	$ sudo sed -i -e 's|#HRNGDEVICE=/dev/hwrng|HRNGDEVICE=/dev/urandom|' /etc/default/rng-tools 
  	$ sudo service rng-tools start

- generate a key:

  	$ gpg --gen-key

- to view all keys:

  	$ gpg --list-keys

- export a public key:

  	$ gpg --export -a --output [path-to-public-key].asc [email-address]

- export a secret key:

  	$ gpg -a --export-secret-keys > [path-to-secret-key].asc

KeyBasedFileProcessor

A simple utility class that encrypts/decrypts public key based encryption files.

To encrypt a file: KeyBasedFileProcessor -e [-a|-ai] fileName publicKeyFile.
If -a is specified the output file will be "ascii-armored". If -i is specified the output file will be have integrity checking added.

To decrypt: KeyBasedFileProcessor -d fileName secretKeyFile passPhrase.

Note 1: this example will silently overwrite files, nor does it pay any attention to the specification of "_CONSOLE" in the filename. It also expects that a single pass phrase will have been used.

Note 2: if an empty file name has been specified in the literal data object contained in the encrypted packet a file with the name filename.out will be generated in the current working directory.



