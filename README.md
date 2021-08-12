# :clipboard: Hasher 
Simple command-line java program to compare file hashes.
Supports all the hashing algorihms supported by Java's [MessageDigest](https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/security/MessageDigest.html) object.

## Launch arguments
| Flag | Description | Required | Value parameter |Default Value |
|------|-------------|----------|----------|--------|
| `-f` | File to hash and compare | Yes | Yes | N/A |
| `-s` | Whether to silence all `System.out` output | No | No | False |
| `-h` | The hash to compare the file hash against. | Yes | Yes | N/A |
| `-a` | The hashing algorithm to use when hashing the file. | No | Yes | SHA-256 |

## Exit Codes
| Exit Code | Meaning |
|-|-|
| `0` | Application ran successfully and the given hash matches.|
| `1` | Application ran successfully but the given hash did not match. |
| `-1` | Application did not run successfully. No hash was compared. |

In the case of a `-1` status code, check the console for any error messages.
