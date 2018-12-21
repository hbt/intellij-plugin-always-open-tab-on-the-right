# Macro tests to reproduce bug and solve the fix

## reproduce bug

Bug is: when reopening tabs, it reopens them in the old location instead of on the right.
It is not just reopening though; there is other cases but not necessary to write a macro for each.

```
# init data 

cp -R data/fakerepo /home/hassen/IdeaProjects/untitled1

# when running the IDE plugin for first time. Open that folder in the IDE
# disable project component in resources/META-INF/plugin.xml:29

# map macro script key 

scripts/reproduce-bug-macro.sh

```


## test bug fix

tab should always open on the right

```
# same init procedure (view above)

# map macro script key 

# same script -- just check T2 appears *after* TT3 instead of before
scripts/fix-bug-macro.sh

```
