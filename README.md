# CSSE3030 Week 9 — Part 4: Regression Test Selection with Ekstazi

This is a small Maven project that lets you check your hand-derived answers from
earlier in the Week 9 practical against the output of a real Regression Test
Selection (RTS) tool, [Ekstazi](https://github.com/gliga/ekstazi).

It contains the same `ShippingFee` example from the worksheet, plus the
`FeeCalculator` / `PremiumFeeCalculator` dynamic-dispatch follow-up, wired up
so that `./mvnw test` runs only the tests Ekstazi selects.

## 0. Requirements

- A JDK on your `PATH` — **8, 11, 17, or 21** all work. No other setup: this
  repo uses the **Maven Wrapper** (`./mvnw`), so you don't need Maven
  installed, and the JVM flags Ekstazi needs are already baked into
  `.mvn/jvm.config` and `pom.xml`.

  **Windows users:** run `./mvnw test`, not `mvnw test`. If you're using
  Command Prompt instead of PowerShell/Git Bash, use `mvnw.cmd test` instead.

- **Windows only — if any part of the path to this project has a space in it**
  (commonly your username, e.g. `C:\Users\First Last\...`): the build will
  crash with `Error opening zip file or JAR manifest missing`. Fix it once,
  from the project root, in PowerShell:

  ```powershell
  New-Item -ItemType Directory -Path ".mvnrepo" -Force
  New-Item -ItemType Junction -Path "C:\mvnrepo-csse3030" -Target "$PWD\.mvnrepo"
  ```

  Then **always build** with:

  ```powershell
  ./mvnw.cmd test "-Dmaven.repo.local=C:\mvnrepo-csse3030"
  ```

  If your path has no spaces, ignore this and just run `./mvnw test`.


**Important:** Ekstazi only compares against whatever it recorded on the
*last* run — it has no idea what "V1" is. That means reverting a file and
then immediately editing a *different* file, without running `./mvnw test`
in between, makes the revert itself look like a change, and the next
result will include tests for both files. Whenever a step says "discard the
edit," always follow it with one `./mvnw test` run before making the next
edit, even though that run's own output doesn't matter for the exercise.

## 1. Baseline run

**Task.** Run `./mvnw test` (or `./mvnw.cmd test "-Dmaven.repo.local=C:\mvnrepo-csse3030"`
if you are using Windows and had to redirect your maven repo) twice in a row, with 
no code changes in between.
What runs the first time? What runs the second time, and why?

(The first run creates a hidden `.ekstazi/` directory in the project root —
Ekstazi's dependency database. Don't delete it; the rest of the exercise
depends on it being there.)

## 2. Selecting after a change

Change `fee += 3` to `fee += 6` in `inline/ShippingFee.java` (the fragile
branch of `compute()`) only. Leave everything under `dispatch/` untouched —
that's covered in step 3. Update the expected values in
`inline/ShippingFeeTest.java` to match.

**Task.** Run `./mvnw test`. Which tests ran? Does this match your
hand-derived answer to Part 1, Question 2?

When you're done, discard both edits and run `./mvnw test` once more before
making the next edit.

## 3. Dynamic dispatch and inheritance

**Task.** Apply the change to `FeeCalculator` only (`return fee + 3;` →
`return fee + 6;` in `applyFragileFee`), updating the expected value in
`ShippingFeeWithFeeCalculatorTest.java` to match, and run the suite. Which
of the `dispatch` tests ran?

Then discard both edits and run `./mvnw test` once more before making the
next edit.

Once re-baselined, apply the same change to `PremiumFeeCalculator` only
instead (leaving `FeeCalculator` untouched), updating the expected value in
`ShippingFeeWithPremiumFeeCalculatorTest.java` to match, and run again. Which
test ran this time? Is this result symmetric with the `FeeCalculator` run
above?

When you're done, discard both edits and run `./mvnw test` again.

## 4. Inspect Ekstazi's dependency files

Open the `.ekstazi/` directory in the project root. You should see one
`.clz` file per test class, e.g.:

```
.ekstazi/dispatch.ShippingFeeWithFeeCalculatorTest.clz
.ekstazi/dispatch.ShippingFeeWithPremiumFeeCalculatorTest.clz
.ekstazi/inline.ShippingFeeTest.clz
```

Each line lists one dependency as a `file:` URL to a `.class` file, plus a
checksum.

**Task.** Open both `dispatch` `.clz` files and compare them. Does
`FeeCalculator.class` show up in both, even though
`ShippingFeeWithPremiumFeeCalculatorTest` never calls `FeeCalculator`'s own
version of `applyFragileFee` directly? Why might that be, given how Java
classes get loaded?

## Troubleshooting

- **No `T E S T S` banner and no tests ran**: see step 1 — this is the
  behaviour that task is asking you to explain
- **You want to force a full re-run**: delete the `.ekstazi/` directory and
  run `./mvnw test` again. This throws away Ekstazi's dependency history and
  re-baselines from scratch.
