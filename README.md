# :clipboard: Hasher 
#### Simple command-line java program to compare file hashes.
---
![Written in Java](https://img.shields.io/badge/written%20in-java-brightgreen)
![Code Climate maintainability](https://img.shields.io/codeclimate/maintainability/sirmeepington/hasher)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/sirmeepington/hasher/Maven%20Validation)
![Code Climate coverage](https://img.shields.io/codeclimate/coverage-letter/sirmeepington/hasher)
---

## Launch arguments
| Flag | Long ver. | Description | Required | Value parameter |Default Value |
|------|---------- |---|----------|----------|--------|
| `-f` | `--file`|File to hash and compare | Yes | Yes | N/A |
| `-s` | `--silent`|Whether to silence all `System.out` output | No | No | False |
| `-h` | `--hash`|The hash to compare the file hash against. | Yes | Yes | N/A |
| `-a` | `--algorithm`|The hashing algorithm to use when hashing the file. | No | Yes | SHA-256 |
| `-la`| `--list-algorithms`|Lists available hashing algorithms. | No | No | N/A |
| `-help`| `--help`|Shows the command help / usage. | No | No | N/A |

A list of all available commands can be found by using the `-la`/`--list-algorithms` parameter.

## Exit Codes
| Exit Code | Meaning |
|-|-|
| `0` | Application ran successfully and the given hash matches.|
| `1` | Application ran successfully but the given hash did not match. |
| `-1` | Application did not run successfully. No hash was compared. |

In the case of a `-1` status code, check the console for any error messages.
